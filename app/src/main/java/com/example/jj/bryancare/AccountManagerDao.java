package com.example.jj.bryancare;

import android.content.Context;

/**
 * Created by zAdmin on 5/4/2017.
 */

public interface AccountManagerDao {
    public void login(final Context context, final String nric, final String password);
    public void createAccount(final Context context, final String nric, final String password, final String email);
    public void addToQueue(String polyclinic, String queue, int queueNumber, int queuePosition);
    public void removeFromQueue();
    public void setCurrentUser(User currentUser);
    public User getCurrentUser();
}
