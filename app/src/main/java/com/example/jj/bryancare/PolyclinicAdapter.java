package com.example.jj.bryancare;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JJ on 19/3/2017.
 */

public class PolyclinicAdapter extends ArrayAdapter<Polyclinic> {

    private String filter;
    private static final int AVG_CONSULTATION_TIME = 15;

    public PolyclinicAdapter(Context context, ArrayList<Polyclinic> polyclinics, String filter) {
        super(context, 0, polyclinics);
        this.filter = filter;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_polyclinic, parent, false);
        }

        final Polyclinic polyclinic = getItem(position);

        TextView polyclinicNameTextView = (TextView) listItemView.findViewById(R.id.textViewPolyclinicName);
        polyclinicNameTextView.setText(polyclinic.getName());

        TextView queueTimeTextView = (TextView) listItemView.findViewById(R.id.textViewQueueTime);
        queueTimeTextView.setText(displayFilterQueue(filter, polyclinic, AVG_CONSULTATION_TIME)+" mins");

        TextView viewRouteTextView = (TextView) listItemView.findViewById(R.id.textViewViewRoute);
        viewRouteTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Uri googleMapsUri = Uri.parse("google.navigation:q=" + polyclinic.getLocation());
                Intent viewRouteIntent = new Intent(android.content.Intent.ACTION_VIEW, googleMapsUri);
                viewRouteIntent.setPackage("com.google.android.apps.maps");

                if (viewRouteIntent.resolveActivity(getContext().getPackageManager()) != null) {
                    getContext().startActivity(viewRouteIntent);
                }

            }
        });


        return listItemView;
    }

   public int displayFilterQueue(String filter, Polyclinic polyclinic, int avgConsultationTime) {
       int queueTime = 0;
       switch (filter) {
           case "Average Wait Time": {
               queueTime = polyclinic.getAverageQueue() * avgConsultationTime;
               break;
           }
           case "General Ailments": {
               queueTime = polyclinic.getGeneralAilments() * avgConsultationTime;
               break;
           }
           case "Medical Review": {
               queueTime = polyclinic.getMedicalReview() * avgConsultationTime;
               break;
           }
           case "Medical Tests": {
               queueTime = polyclinic.getMedicalTests() * avgConsultationTime;
               break;
           }
           case "Dental": {
               queueTime = polyclinic.getDental() * avgConsultationTime;
               break;
           }
           case "Others": {
               queueTime = polyclinic.getOthers() * avgConsultationTime;
               break;
           }
       }
       return queueTime;
   }

}
