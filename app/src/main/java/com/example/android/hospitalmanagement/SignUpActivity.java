package com.example.android.hospitalmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;



public class SignUpActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    private static final int RC_SIGN_IN = 9001;
    private static int person;

    GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;

    public static String email;
    public  String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestEmail()
                .build();


        // Build a GoogleApiClient with access to the Google Sign-In API and the
// options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



        SignInButton patSignUp=(SignInButton) findViewById(R.id.pat_sign_up);
        patSignUp.setSize(patSignUp.SIZE_STANDARD);

        patSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.pat_sign_up:
                        person=1;
                        patSignIn();
                        break;
                }

            }
        });


        TextView signin=(TextView) findViewById(R.id.sign_in_button);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(i);
            }
        });
    }







    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(person==0)
            handleSignInResult(result);
            else
                handlePatSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("SignUp", "---------------handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            email=acct.getEmail();
            userName=acct.getDisplayName();
            Intent intent=new Intent(this,DoctorDetails.class);
            startActivity(intent);
            Toast.makeText(this,"Logged in as "+ email,Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(this,"Error signing in!!",Toast.LENGTH_SHORT).show();

        }

            // Signed out, show unauthenticated UI.
           /// updateUI(false);
        }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Toast.makeText(this,"Error signing in!! Try Again",Toast.LENGTH_SHORT).show();

    }


    private void patSignIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    private void handlePatSignInResult(GoogleSignInResult result) {
        Log.d("SignUp", "---------------handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            email=acct.getEmail();
            userName=acct.getDisplayName();
            Intent intent=new Intent(this,PatientDetails.class);
            startActivity(intent);
            Toast.makeText(this,"Logged in as "+ email,Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(this,"Error signing in!!",Toast.LENGTH_SHORT).show();

        }

        // Signed out, show unauthenticated UI.
        /// updateUI(false);
    }
    public String getEmail(){ return email;}
}


