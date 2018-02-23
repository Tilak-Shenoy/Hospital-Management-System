package com.example.android.hospitalmanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.hospitalmanagement.data.DetailsContract.PatientEntry;
import com.example.android.hospitalmanagement.data.DetailsDbHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class PatientDetails extends AppCompatActivity {

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Button buttonSelect;
    private ImageView displayImage;
    private String userChoosenTask;
    private Uri filePath;
    private String imageUrl;

    EditText patName;
    EditText mobileNo;
    EditText address;
    EditText insurance;
    EditText pass;
    EditText fileNumber;
    //creating reference to firebase storage
    private StorageReference storageRef= FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_details);

        patName = (EditText) findViewById(R.id.name);

        //Set up button to import pictures from gallery or take a picture from camera
        buttonSelect = (Button) findViewById(R.id.propic);
        buttonSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        displayImage = (ImageView) findViewById(R.id.image);


        mobileNo = (EditText) findViewById(R.id.mobNo);


        address = (EditText) findViewById(R.id.address);

        insurance= (EditText) findViewById(R.id.insurance);

        pass=(EditText) findViewById(R.id.password);
        fileNumber=(EditText) findViewById(R.id.fileNum);

        Button submit = (Button) findViewById(R.id.submit);
        //TODO:check conditions and upload to database and go to patient activity
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }


    private  void submitDetails(){
        String patname = patName.getText().toString();
        SignUpActivity signUp=new SignUpActivity();
        String patEmail=signUp.email;
        String patPassword=pass.getText().toString();
        String patmobileNo = mobileNo.getText().toString();
        String patAddress = address.getText().toString();
        String patInsurance = insurance.getText().toString();
        String patFileNum=fileNumber.getText().toString();

        insertPatient(patname,patEmail,patPassword,imageUrl, patmobileNo,patAddress,patInsurance,patFileNum);

    }




    public void insertPatient(String name, String email, String password, String imageUrl, String phone, String address, String insurance,String fileNum){

        DetailsDbHelper dbHelper=new DetailsDbHelper(this);
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues values=new ContentValues();

        values.put(PatientEntry.COLUMN_NAME,name);
        values.put(PatientEntry.COLUMN_EMAIL,email);
        values.put(PatientEntry.COLUMN_PASSWORD,password);
        values.put(PatientEntry.COLUMN_PHOTO,imageUrl);
        values.put(PatientEntry.COLUMN_PHONE,phone);
        values.put(PatientEntry.COLUMN_ADDRESS,address);
        values.put(PatientEntry.COLUMN_INSURANCE,insurance);
        values.put(PatientEntry.COLUMN_FILE,fileNum);

        long newRowId = db.insert(PatientEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving details", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, " Details saved ", Toast.LENGTH_SHORT).show();
            Log.d("TABLE_NAME",PatientEntry.TABLE_NAME);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(PatientDetails.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(PatientDetails.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        filePath= Uri.fromFile(destination);
        displayImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        filePath=data.getData();
        displayImage.setImageBitmap(bm);
    }

    private void uploadImage(){
        if(filePath != null) {
            final ProgressDialog pd=new ProgressDialog(this);
            pd.setMessage("Loading...");
            pd.show();

            // StorageReference storageRef=storage.getReference();
            StorageReference childRef = storageRef.child(patName.getText().toString());

            //uploading the image
            final UploadTask uploadTask = childRef.putFile(filePath);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.dismiss();
                    imageUrl= String.valueOf(taskSnapshot.getMetadata().getDownloadUrl());
                    Toast.makeText(PatientDetails.this, "Upload successful", Toast.LENGTH_SHORT).show();
                    submitDetails();
                    Intent patient_intent = new Intent(PatientDetails.this, PatientActivity.class);
                    startActivity(patient_intent);
                    Log.e("imageUrl",imageUrl);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(PatientDetails.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(PatientDetails.this, "Select an image", Toast.LENGTH_SHORT).show();
        }
    }

}
