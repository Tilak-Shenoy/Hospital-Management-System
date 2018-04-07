package com.example.android.hospitalmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ViewAnimator;

import java.util.ArrayList;

public class Pharmacy extends AppCompatActivity {
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);
        getSupportActionBar().setTitle("Pharmacy Assistant Profile");
        Intent i = getIntent();
        email = i.getExtras().getString("email");
        ArrayList<String> test = i.getStringArrayListExtra("arr");

        ListView druglist = (ListView) findViewById(R.id.drugList);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,test);
        druglist.setAdapter(adapter);

    }
    void checkOut(View V){
        Intent med = new Intent (Pharmacy.this,DocMedication.class);
        startActivity(med);

    }
}
