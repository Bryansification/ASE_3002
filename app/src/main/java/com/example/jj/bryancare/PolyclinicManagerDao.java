package com.example.jj.bryancare;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by zAdmin on 5/4/2017.
 */

public interface PolyclinicManagerDao {
    public void loadPolyclinicList(final Context context, String queueFilter);
    public ArrayList<Polyclinic> getPolyclinics();
    public PolyclinicAdapter getPolyclinicAdapter();
    public void loadQueueInfo(String queue, final TextView waitingTime);
    public void loadTimeLeft(final String polyclinicName, final String queue, final int queuePosition, final TextView timeLeftTextView);

    public void addToQueue(final String queue, final int queueNumber);
    public void removeFromQueue(final String polyclinic, final String queue);
    public void setCurrentPolyclinic(String polyclinicName);
    public void setCurrentPolyclinic(Polyclinic currentPolyclinic);
    public Polyclinic getCurrentPolyclinic();
}
