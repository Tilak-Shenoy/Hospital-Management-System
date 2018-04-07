package com.example.android.hospitalmanagement;


public class Patient {
    private String name;
    private String password;
    private String email;

    public Patient(){}

    public Patient(String name, String email,String password){

        this.name = name;
        this.email=email;
        this.password=password;
    }

    public String getPatientname() {return name;}
    public String getPatientEmail(){return  email;}
    public String getPatientPass(){return  password;}


    public void setPatientname(String patientname){
        this.name=patientname;
    }
    public void setPass(String pass){
        this.password=pass;
    }
    public void setPatientEmail(String patientEmail){
        this.email=patientEmail;
    }
}
