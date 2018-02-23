package com.example.android.hospitalmanagement.data;

import android.provider.BaseColumns;

/**
 * Created by tilak on 10/8/17.
 */

public class DetailsContract {

    public DetailsContract(){}

    public class DoctorEntry implements BaseColumns{

        public final static String TABLE_NAME="doctor";
        public final static String COLUMN_ID=BaseColumns._ID;
        public final static String COLUMN_NAME="name";
        public final static String COLUMN_EMAIL="email";
        public final static String COLUMN_PASSWORD="password";
        public final static String COLUMN_PHOTO="photo";
        public final static String COLUMN_HOSPITAL="hospital";
        public final static String COLUMN_PHONE="phone";
        public final static String COLUMN_DEPT="department";


    }

    public class PatientEntry implements BaseColumns{
        public final static String TABLE_NAME="patient";
        public final static String COLUMN_ID=BaseColumns._ID;
        public final static String COLUMN_NAME="name";
        public final static String COLUMN_EMAIL="email";
        public final static String COLUMN_PASSWORD="password";
        public final static String COLUMN_ADDRESS="address";
        public final static String COLUMN_PHOTO="photo";
        public final static String COLUMN_FILE="fileNum";
        public final static String COLUMN_INSURANCE="insurance";
        public final static String COLUMN_PHONE="phone";
    }
}
