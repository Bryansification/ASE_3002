package com.example.jj.bryancare;

/**
 * Created by JJ on 17/3/2017.
 */

public class User {

    private String nric;
    private String password;
    private String email;

    private String polyclinic;
    private String queue;
    private int queueNumber;
    private int queuePosition;

    public User() {}

    public User(String nric, String password, String email) {
        this.nric = nric;
        this.password = password;
        this.email = email;
        this.polyclinic = "NONE";
        this.queue = "NONE";
        this.queueNumber = -1;
        this.queuePosition = -1;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNric() { return nric; }

    public String getPolyclinic() { return polyclinic;}

    public String getQueue() { return queue; }

    public int getQueueNumber() { return queueNumber; }

    public int getQueuePosition() {return queuePosition; }

    public void setPolyclinic(String polyclinic) {this.polyclinic = polyclinic; }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public void setQueueNumber(int queueNumber) {
        this.queueNumber = queueNumber;
    }

    public void setQueuePosition(int queuePosition) {this.queuePosition = queuePosition;}
}
