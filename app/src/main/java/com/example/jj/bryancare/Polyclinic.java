package com.example.jj.bryancare;

/**
 * Created by JJ on 17/3/2017.
 */

public class Polyclinic {

    private String name;
    private String location;

    private int generalAilments;
    private int medicalTests;
    private int medicalReview;
    private int dental;
    private int others;

    public Polyclinic() {}


    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getAverageQueue() {return (generalAilments + medicalTests + medicalReview + dental + others)/5;}

    public int getGeneralAilments() {return generalAilments;}

    public int getMedicalTests() {return medicalTests;}

    public int getMedicalReview() {return medicalReview;}

    public int getDental() {return dental;}

    public int getOthers() {return others;}

}
