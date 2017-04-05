package com.example.jj.bryancare;

/**
 * Created by JJ on 23/3/2017.
 */

public class DataStore {

    private static User currentUser;
    private static Polyclinic currentPolyclinic;

    public static void setCurrentUser(User currentUser) {DataStore.currentUser = currentUser;}
    public static void setCurrentPolyclinic (Polyclinic currentPolyclinic) {DataStore.currentPolyclinic = currentPolyclinic;}

    public static User getCurrentUser() {return currentUser;}
    public static Polyclinic getCurrentPolyclinic() {return currentPolyclinic;}


}
