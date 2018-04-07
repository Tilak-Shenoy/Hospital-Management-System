package com.example.android.hospitalmanagement;

/**
 * Created by nac on 7/4/18.
 */

public class Medication {
    private String patientEmail;
    private String patientMedication;
    private String patientComment;

    Medication(){}

    Medication(String email, String meds, String comment){
        this.patientComment =comment;
        this.patientEmail = email;
        this.patientMedication = meds;
    }

    public String getPatientEmail(){ return patientEmail;}
    public String getPatientMedication(){ return patientMedication;}
    public String getPatientComment(){ return patientComment;}


}
