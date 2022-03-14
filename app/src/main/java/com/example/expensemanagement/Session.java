package com.example.expensemanagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setUserID(String userID) {
        prefs.edit().putString("userID", userID).commit();
    }

    public String getUserID() {
        String userName = prefs.getString("userID","");
        return userName;
    }
}
