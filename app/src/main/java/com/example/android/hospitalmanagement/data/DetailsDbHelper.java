package com.example.android.hospitalmanagement.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.hospitalmanagement.data.DetailsContract.DoctorEntry;
import com.example.android.hospitalmanagement.data.DetailsContract.PatientEntry;


/**
 * Created by tilak on 10/8/17.
 */

public class DetailsDbHelper extends SQLiteOpenHelper {


    private final static String DB_NAME="details.db";

    private final static int DB_VERSION=1;


    public DetailsDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_DOCTOR_TABLE="CREATE TABLE "+ DoctorEntry.TABLE_NAME+" ("+
                DoctorEntry.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                DoctorEntry.COLUMN_NAME+" STRING, "+
                DoctorEntry.COLUMN_EMAIL+" STRING, "+
                DoctorEntry.COLUMN_PASSWORD+" STRING, "+
                DoctorEntry.COLUMN_PHOTO+" STRING, "+
                DoctorEntry.COLUMN_PHONE+" INTEGER, "+
                DoctorEntry.COLUMN_HOSPITAL+" STRING, "+
                DoctorEntry.COLUMN_DEPT+" STRING);";

        String CREATE_PATIENT_TABLE="CREATE TABLE "+ PatientEntry.TABLE_NAME+" ("+
                PatientEntry.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                PatientEntry.COLUMN_NAME+" STRING, "+
                PatientEntry.COLUMN_EMAIL+" STRING, "+
                PatientEntry.COLUMN_PASSWORD+" STRING, "+
                PatientEntry.COLUMN_PHOTO+" STRING, "+
                PatientEntry.COLUMN_PHONE+" INTEGER, "+
                PatientEntry.COLUMN_INSURANCE+" STRING, "+
                PatientEntry.COLUMN_FILE+" STRING, "+
                PatientEntry.COLUMN_ADDRESS+" STRING);";


        db.execSQL(CREATE_DOCTOR_TABLE);
        db.execSQL(CREATE_PATIENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



}
