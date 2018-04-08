package com.example.android.hospitalmanagement;

import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;


public class Doctor  implements Serializable{


    private String docName;
    private String docDept;
    private String docEmail;
    private String docPassword;
    private int i;

    public Doctor(String name, String email, String password,String dept,int i)
    {
        docName=name;
        docDept=dept;
        docEmail=email;
        docPassword=password;
        this.i=1;

    }

    public String getDocName() {return docName;}
    public String getDocDept() {return docDept;}
    public String getEmail() {return  docEmail;}
    public String getPassword() {return docPassword;}
    public int getI(){return i;}


}
