package com.example.android.hospitalmanagement;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by harsh on 3/4/17.
 */

public class PatientAdapter extends ArrayAdapter<Patient> {

    public PatientAdapter(Context context, ArrayList<Patient> patient)
    {
        super(context,0,patient);
        for(int i=0;i<8;i++) {
            patient.add(new Patient("Dr.A", "A", R.drawable.common_google_signin_btn_text_light_focused, "hello"));
            patient.add(new Patient("Dr.B", "B", R.drawable.ic_menu_gallery, "hello1"));
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView==null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.patient_list_item,parent,false);
        }

        Patient currentPatient = getItem(position);

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.doctorName);
        nameTextView.setText(currentPatient.getPatientname());

        TextView docEmail = (TextView) listItemView.findViewById(R.id.doctorEmail);
        docEmail.setText(currentPatient.getPatientdept());

        TextView hospitalTextView = (TextView) listItemView.findViewById(R.id.doctorHospital);
        hospitalTextView.setText(currentPatient.getPatienthospital());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.doctorImage);
        imageView.setImageResource(currentPatient.getPatientimage());

        TextView emailTextView=(TextView) listItemView.findViewById(R.id.doctorEmail);
        emailTextView.setText(currentPatient.getPatientEmail());

        return listItemView;
    }
}
