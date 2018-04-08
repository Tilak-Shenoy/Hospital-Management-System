package com.example.android.hospitalmanagement;

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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        final String state= (String) getIntent().getExtras().get("state");

        if(state.equals("past"))
            getSupportActionBar().setTitle("Past Appointments");
        else if (state.equals("scheduled"))
            getSupportActionBar().setTitle("Scheduled Appointments");

        ListView list=(ListView) findViewById(R.id.container);

        final ArrayList appointments= (ArrayList) getIntent().getSerializableExtra("Appointments");

        if(appointments.isEmpty()){

            TextView textView=(TextView) findViewById(R.id.noApp);
            textView.setVisibility(View.VISIBLE);
        }

        DoctorAdapter adapter=new DoctorAdapter(this,appointments);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent=new Intent(DoctorActivity.this,AppDetails.class);
                intent.putExtra("Appointments",appointments);
                intent.putExtra("app",(Appointment) appointments.get(i));
                intent.putExtra("flag","doctor");
                intent.putExtra("state",state);
                startActivity(intent);
            }
        });
    }
}