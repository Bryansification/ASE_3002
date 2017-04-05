package com.example.jj.bryancare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class TakeQueueNumberActivity extends AppCompatActivity {

    private String queue;
    private PolyclinicManagerDao polyclinicManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_queue_number);

        polyclinicManager = new PolyclinicManager();

        TextView polyclinicTitle = (TextView) findViewById(R.id.textViewPolyclinicName);
        polyclinicTitle.setText(polyclinicManager.getCurrentPolyclinic().getName()+" Polyclinic");

        Spinner queueSelectSpinner = (Spinner) findViewById(R.id.spinnerQueueSelect);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.purpose_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        queueSelectSpinner.setAdapter(adapter);

        queueSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                queue = parent.getItemAtPosition(position).toString();
                queue = queue.replaceAll("\\s","");
                queue = Character.toLowerCase(queue.charAt(0)) + queue.substring(1);
                loadQueueInfo(queue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                queue = "Nothing Selected";
            }
        });

        TextView viewRouteTextView = (TextView) findViewById(R.id.textViewViewRoute);
        viewRouteTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Uri googleMapsUri = Uri.parse("google.navigation:q=" + polyclinicManager.getCurrentPolyclinic().getLocation());
                Intent viewRouteIntent = new Intent(android.content.Intent.ACTION_VIEW, googleMapsUri);
                viewRouteIntent.setPackage("com.google.android.apps.maps");

                if (viewRouteIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(viewRouteIntent);
                }

            }
        });
    }

    private void loadQueueInfo(String queue) {

        TextView waitingTime = (TextView) findViewById(R.id.textViewWaitingTime);
        polyclinicManager.loadQueueInfo(queue, waitingTime);

    }

    public void takeQueueNumber(View view) {

        if(queue.matches("Nothing Selected")) {
            Toast.makeText(getApplicationContext(), "Please select the purpose of visit", Toast.LENGTH_SHORT).show();
        } else {

            Random random = new Random();
            int queueNumber = random.nextInt(9)*1000+random.nextInt(9)*100+random.nextInt(9)*10+random.nextInt(9);
            polyclinicManager.addToQueue(queue, queueNumber);

            Intent goToQueueUpdateIntent = new Intent(TakeQueueNumberActivity.this, QueueUpdateActivity.class);
            goToQueueUpdateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(goToQueueUpdateIntent);
        }

    }

}
