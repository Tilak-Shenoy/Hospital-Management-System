package com.example.android.hospitalmanagement;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by nac on 5/4/18.
 */

public class Appointment implements Serializable {
    private String doctorName;
    private String date;
    private long timeSlot;
    private String description;
    private  String dept;
    private String email;

    public Appointment(String doctorName,String date, long time, String dept,String descrip,String email){
        this.doctorName = doctorName;
        this.date = date;
        this.timeSlot = time;
        this.description = descrip;
        this.dept = dept;
        this.email = email;

    }

    public String getName() {return doctorName;}
    public String getDate() {return date;}
    public String getDescrip() {return description;}
    public long getTime() {return timeSlot;}
    public String getEmail(){return email;}
    public String getDept(){return dept;}

}
