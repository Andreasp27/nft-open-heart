package com.example.nft.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setAccessToken(String accessToken) {
        prefs.edit().putString("access_token", accessToken).commit();
    }

    public String getAccessToken() {
        String accessToken = prefs.getString("access_token","");
        return accessToken;
    }

    public void setBase(String base) {
        prefs.edit().putString("base", base).commit();
    }

    public String getBase() {
        String base = prefs.getString("base","");
        return base;
    }

    public void setId(String id) {
        prefs.edit().putString("id", id).commit();
    }

    public String getId() {
        String id = prefs.getString("id","");
        return id;
    }
}
