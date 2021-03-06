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

public class DoctorAdapter extends ArrayAdapter<Appointment> {

    public DoctorAdapter(Context context, ArrayList<Appointment> Appt)
    {
        super(context,0,Appt);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView==null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.patient_list_item,parent,false);
        }

        Appointment currentAppt = getItem(position);

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.doctorName);
        nameTextView.setText(currentAppt.getName());

        TextView depart = (TextView) listItemView.findViewById(R.id.dept);
        depart.setText(currentAppt.getDept());

        TextView date = (TextView) listItemView.findViewById(R.id.date);
        date.setText(currentAppt.getDate());

        return listItemView;
    }
}
