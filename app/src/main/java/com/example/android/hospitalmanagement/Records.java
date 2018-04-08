package com.example.android.hospitalmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Records extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        getSupportActionBar().setTitle("Pharmacy Assistant Profile");
        Intent i = getIntent();
        ArrayList<String> doctors = new ArrayList<String>();
        doctors.add("Dr Stephen Strange");
        doctors.add("Dr Lacy Windham");
        doctors.add("Dr Batra");
        doctors.add("Dr Tom Hardy");
        doctors.add("Dr Bhausingh");
        doctors.add("Dr Srinivas Iyer");

        ListView docList = (ListView) findViewById(R.id.docList);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,doctors);
        docList.setAdapter(adapter);
    }
}
