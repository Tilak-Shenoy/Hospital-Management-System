package com.example.android.hospitalmanagement;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import org.w3c.dom.Text;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;


public class AppDetails extends AppCompatActivity {
    ArrayList appointments;
    TextView name,date,time,dept,desc,Pname;
    String DelDate,flag;
    Appointment appointment;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    Button prescribe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_details);

        name=(TextView) findViewById(R.id.AppName);
        date=(TextView) findViewById(R.id.AppDate);
        time= (TextView) findViewById(R.id.AppTime);
        dept= (TextView) findViewById(R.id.AppDept);
        desc= (TextView) findViewById(R.id.AppDesc);
        Pname=(TextView) findViewById(R.id.PatientName);
        Button cancel=(Button) findViewById(R.id.cancel);
         prescribe= (Button) findViewById(R.id.meds);

        flag=getIntent().getExtras().getString("flag");
        appointments= (ArrayList) getIntent().getSerializableExtra("Appointments");
        appointment= (Appointment) getIntent().getSerializableExtra("app");
        name.setText(appointment.getName());
        DelDate = appointment.getDate();
        date.setText(DelDate);
        Pname.setText(appointment.getPatientName());
      //  time.setText("2:00pm");
        Log.d("time", String.valueOf(appointment.getTime()));
        if(appointment.getTime()==4)
            time.setText("4:00 pm");
        else if(appointment.getTime()==5)
            time.setText("5:00 pm");
        else if(appointment.getTime()==6)
            time.setText("6:00 pm");
        dept.setText(appointment.getDept());
        desc.setText(appointment.getDescrip());

        Date curr = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-mm-yyyy");
        String cu = df.format(curr);
        try {
            curr=df.parse(cu);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date cdate = null;

        try {
            cdate = df.parse(appointment.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(cdate.before(curr)){
            cancel.setVisibility(View.INVISIBLE);
        }

        if( flag.equals("patient")){
            prescribe.setVisibility(View.INVISIBLE);
        }

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("Meds");
        mFirebaseDatabase.orderByChild("date").equalTo(DelDate).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TextView med;
                String meds="No Medication Prescribed";
                if (dataSnapshot.exists()) {

                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        meds= (String) issue.child("patientMedication").getValue();
                    }


                }
                med=(TextView) findViewById(R.id.med);
                med.setText(meds);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.d("true","false");

            }
        });

    }
    public void onClick(View v){
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("appointments");

        mFirebaseDatabase.orderByChild("date").equalTo(DelDate).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        issue.getRef().removeValue();
                    }
                    Intent intent=new Intent(AppDetails.this,PatientActivity.class);
                    appointments.remove(appointment);
                    intent.putExtra("Appointments",appointments);
                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.d("true","false");

            }
        });




    }

    public void prescribe(View v){
        Intent it=new Intent(AppDetails.this,DoctorPrescription.class);
        it.putExtra("currentAppt",appointment);
        it.putExtra("Appointments",appointments);

        startActivity(it);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(flag.equals("patient")){
        Intent intent=new Intent(AppDetails.this,PatientActivity.class);
        intent.putExtra("Appointments",appointments);
    }
    else{
            Intent intent=new Intent(AppDetails.this,DoctorActivity.class);
            intent.putExtra("Appointments",appointments);
        }
    }

}
