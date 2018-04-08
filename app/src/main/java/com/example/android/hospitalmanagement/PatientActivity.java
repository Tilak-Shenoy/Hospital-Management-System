package com.example.android.hospitalmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PatientActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
   // private SectionsPagerAdapter mSectionsPagerAdapter;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabLayout tabLayout;



    static String email,name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        String state= (String) getIntent().getExtras().getString("state");
        if(state.equals("past"))
            getSupportActionBar().setTitle("Past Appointments");
        else if (state.equals("scheduled"))
            getSupportActionBar().setTitle("Scheduled Appointments");

        ListView list=(ListView) findViewById(R.id.container);

        Intent i = getIntent();
        email = i.getExtras().getString("email");
        name = i.getExtras().getString("name");
        final ArrayList appointments= (ArrayList) i.getSerializableExtra("Appointments");
        Log.d("LIST",appointments.toString());
        if(!appointments.isEmpty()) {
            PatientAdapter adapter = new PatientAdapter(this, appointments);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(PatientActivity.this, AppDetails.class);
                    intent.putExtra("Appointments", appointments);
                    intent.putExtra("app", (Appointment) appointments.get(i));
                    intent.putExtra("flag", "patient");
                    startActivity(intent);
                }
            });
        }
        else{
            TextView no=(TextView) findViewById(R.id.No);
            no.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onBackPressed() {
        Intent i=new Intent(PatientActivity.this,patientMain.class);
        i.putExtra("name",name);
        i.putExtra("email",email);
        startActivity(i);
    }




}


