package com.example.jj.bryancare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class QueueUpdateActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private AccountManagerDao accountManager;
    private PolyclinicManagerDao polyclinicManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_update);

        accountManager = new AccountManager();
        polyclinicManager = new PolyclinicManager();
        polyclinicManager.setCurrentPolyclinic(accountManager.getCurrentUser().getPolyclinic());

        TextView polyclinicNameTextView = (TextView) findViewById(R.id.textViewPolyclinicName);
        TextView queueNumberTextView = (TextView) findViewById(R.id.textViewQueueNumber);
        TextView queueTextView = (TextView) findViewById(R.id.textViewQueue);
        TextView timeLeftTextView = (TextView) findViewById(R.id.textViewTimeLeft);
        TextView viewRouteTextView = (TextView) findViewById(R.id.textViewViewRoute);

        polyclinicNameTextView.setText(accountManager.getCurrentUser().getPolyclinic());
        queueNumberTextView.setText(accountManager.getCurrentUser().getQueueNumber()+"");
        setDisplayQueue(queueTextView, accountManager.getCurrentUser().getQueue());
        loadTimeLeft(timeLeftTextView);

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

    private void loadTimeLeft(final TextView timeLeftTextView) {

        polyclinicManager.loadTimeLeft(accountManager.getCurrentUser().getPolyclinic(), accountManager.getCurrentUser().getQueue(), accountManager.getCurrentUser().getQueuePosition(), timeLeftTextView);

    }


    private void setDisplayQueue(final TextView queueTextView, String queue) {
        String formatQueue = "";
        String[] formatQueueArray = queue.split("(?=\\p{Upper})");
        formatQueueArray[0] = Character.toUpperCase(formatQueueArray[0].charAt(0)) + formatQueueArray[0].substring(1);
        if(formatQueueArray.length==1) {
            formatQueue = formatQueueArray[0];
        } else {
            formatQueue = formatQueueArray[0] + " " + formatQueueArray[1];
        }

        queueTextView.setText(formatQueue);

    }

    public void leaveQueue(View view) {

        String polyclinic = accountManager.getCurrentUser().getPolyclinic();
        String queue = accountManager.getCurrentUser().getQueue();

        polyclinicManager.removeFromQueue(polyclinic, queue);
        accountManager.removeFromQueue();

        Intent returnToQueueUpdateIntent = new Intent(QueueUpdateActivity.this, BrowsePolyclinicActivity.class);
        returnToQueueUpdateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(returnToQueueUpdateIntent);
    }

}
