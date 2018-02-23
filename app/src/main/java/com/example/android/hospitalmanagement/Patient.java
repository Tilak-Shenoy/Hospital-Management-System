package com.example.android.hospitalmanagement;

/**
 * Created by harsh on 3/4/17.
 */

public class Patient {
    private String patientname;
    private String patientdept;
    private String patienthospital;
    private int patientimage;
    private String patientEmail;

    public Patient(String name, String hospital,int image,String email){

        patientname=name;
        patienthospital=hospital;
        patientimage=image;
        patientEmail=email;
    }

    public Patient(String name,String dept, String hospital,int image,String email)
    {
        patientname=name;
        patientdept=dept;
        patienthospital=hospital;
        patientimage=image;
        patientEmail=email;
    }

    public String getPatientname() {return patientname;}
    public String getPatientdept() {return patientdept;}
    public String getPatienthospital() {return patienthospital;}
    public int getPatientimage() {return patientimage;}
    public String getPatientEmail(){return  patientEmail;}
}
