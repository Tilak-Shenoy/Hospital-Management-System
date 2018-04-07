package com.example.android.hospitalmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by harsh on 4/4/17.
 */

public class AdminActivity extends AppCompatActivity {

    private String name,mobno,fileno,insurance;
    private int image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
    }
    public void onClickRecords(View v){
        Intent nextPage;
        nextPage = new Intent(AdminActivity.this,DoctorDetails.class);
        startActivity(nextPage);

    }
    public void onClickAddDoc(View v){
        Intent nextPage;
        nextPage = new Intent(AdminActivity.this,DoctorDetails.class);
        startActivity(nextPage);

    }

}
