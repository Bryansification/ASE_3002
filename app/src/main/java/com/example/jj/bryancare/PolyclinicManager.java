package com.example.jj.bryancare;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by JJ on 1/4/2017.
 */

public class PolyclinicManager implements PolyclinicManagerDao{

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    private CountDownTimer countDownTimer;
    private ArrayList<Polyclinic> polyclinics;
    private ArrayList<String> polyclinicKeyList;
    private PolyclinicAdapter polyclinicAdapter;

    public void loadPolyclinicList(final Context context, String queueFilter) {

        polyclinics = new ArrayList<>();
        polyclinicKeyList = new ArrayList<>();
        polyclinicAdapter = new PolyclinicAdapter(context, polyclinics, queueFilter);


        database.child("Polyclinics").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Polyclinic polyclinic = dataSnapshot.getValue(Polyclinic.class);
                polyclinics.add(polyclinic);

                String key = dataSnapshot.getKey();
                polyclinicKeyList.add(key);

                polyclinicAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                Polyclinic polyclinic = dataSnapshot.getValue(Polyclinic.class);
                String key = dataSnapshot.getKey();

                int index = polyclinicKeyList.indexOf(key);
                polyclinics.set(index, polyclinic);

                polyclinicAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public ArrayList<Polyclinic> getPolyclinics() {return polyclinics;}

    public PolyclinicAdapter getPolyclinicAdapter() {return polyclinicAdapter;}

    public void loadQueueInfo(String queue, final TextView waitingTime) {

        database.child("Polyclinics").child(getCurrentPolyclinic().getName()).child(queue).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getValue() == null)
                    waitingTime.setText("No Service Provided");

                else {
                    long patients = (long) dataSnapshot.getValue();
                    waitingTime.setText(patients*15+" mins");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void loadTimeLeft(final String polyclinicName, final String queue, final int queuePosition, final TextView timeLeftTextView) {

        database.child("Polyclinics").child(polyclinicName).child(queue).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getValue() == null)
                    timeLeftTextView.setText("No Service Provided");

                else {
                    long patients = (long) dataSnapshot.getValue();

                    if(timeLeftTextView.getText().toString().matches("XX mins")) {

                        countDownTimer = new CountDownTimer((queuePosition - 1) * 900000, 1000) {

                            public void onTick(long millisUntilFinished) {

                                timeLeftTextView.setText("" + String.format("%d:%d mins",
                                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                            }

                            public void onFinish() {
                                timeLeftTextView.setText("Done!");
                            }
                        }.start();
                    }

                    if((int) patients < queuePosition) {

                        AccountManager accountManager = new AccountManager();
                        accountManager.addToQueue(polyclinicName, queue, accountManager.getCurrentUser().getQueueNumber(), (int) patients);
                        countDownTimer.cancel();
                        countDownTimer = new CountDownTimer(((int) patients - 1 )*900000, 1000) {

                            public void onTick(long millisUntilFinished) {

                                timeLeftTextView.setText(""+String.format("%d:%d mins",
                                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                            }

                            public void onFinish() {
                                timeLeftTextView.setText("Done!");
                            }
                        }.start();

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void addToQueue(final String queue, final int queueNumber) {

        database.child("Polyclinics").child(getCurrentPolyclinic().getName()).child(queue).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                long patients = (long) dataSnapshot.getValue();
                int queuePosition = (int) patients+1;

                AccountManager accountManager = new AccountManager();
                accountManager.addToQueue(getCurrentPolyclinic().getName(), queue, queueNumber, queuePosition);

                database.child("Polyclinics").child(getCurrentPolyclinic().getName()).child(queue).setValue(queuePosition);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void removeFromQueue(final String polyclinic, final String queue) {
        database.child("Polyclinics").child(getCurrentPolyclinic().getName()).child(queue).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                long queueLength = (long) dataSnapshot.getValue();

                database.child("Polyclinics").child(getCurrentPolyclinic().getName()).child(queue).setValue(queueLength-1);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setCurrentPolyclinic(String polyclinicName) {
        database.child("Polyclinics").orderByChild("name").equalTo(polyclinicName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Polyclinic polyclinic = dataSnapshot.getValue(Polyclinic.class);
                Log.v("polyclinicManager", dataSnapshot.getKey() + " " +polyclinic.getLocation() +"");
                setCurrentPolyclinic(polyclinic);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                Polyclinic polyclinic = dataSnapshot.getValue(Polyclinic.class);
                setCurrentPolyclinic(polyclinic);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setCurrentPolyclinic(Polyclinic currentPolyclinic) {
        DataStore.setCurrentPolyclinic(currentPolyclinic);
    }

    public Polyclinic getCurrentPolyclinic() {
        return DataStore.getCurrentPolyclinic();
    }

}
