package com.example.android.hospitalmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DocMedication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_prescription);
        getSupportActionBar().setTitle("Doc Medictaion Page");
    }
}
