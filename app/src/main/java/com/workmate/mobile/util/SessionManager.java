package com.workmate.mobile.util;

import android.content.Context;
import android.content.SharedPreferences;

import static com.workmate.mobile.util.Constants.*;


public class SessionManager {
    private SharedPreferences sharedPrefKey;
    private SharedPreferences sharedPrefClockIn;
    private SharedPreferences sharedPrefClockOut;
    private SharedPreferences.Editor sessionKey;
    private SharedPreferences.Editor sessionClockIn;
    private SharedPreferences.Editor sessionClockOut;

    private Context mContext;

    public SessionManager(Context context) {
        this.mContext = context;
        sharedPrefKey = mContext.getSharedPreferences(KEY, 0);
        sharedPrefClockIn = mContext.getSharedPreferences(CLOCK_IN_SHARED_PF, 0);
        sharedPrefClockOut = mContext.getSharedPreferences(CLOCK_OUT_SHARED_PF, 0);

        sessionKey = sharedPrefKey.edit();
        sessionClockIn = sharedPrefClockIn.edit();
        sessionClockOut = sharedPrefClockOut.edit();

    }

    public boolean setKey(String key) {
        sessionKey.putString(KEY, key);
        sessionKey.commit();
        return true;
    }

    public String getKey() {
        return sharedPrefKey.getString(KEY, null);
    }

    public boolean applyClockIn(String clockIn) {
        sessionClockIn.putString(CLOCK_IN_SHARED_PF, clockIn);
        sessionClockIn.commit();
        return true;
    }

    public boolean isClockIn() {
        return sharedPrefClockIn.contains(CLOCK_IN_SHARED_PF);
    }

    public boolean isClockOut() {
        return sharedPrefClockOut.contains(CLOCK_OUT_SHARED_PF);
    }

    public String getClockInStatus() {
        return sharedPrefClockIn.getString(CLOCK_IN_SHARED_PF, "-");
    }

    public boolean applyClockOut(String clockOut) {
        sessionClockOut.putString(CLOCK_OUT_SHARED_PF, clockOut);
        sessionClockOut.commit();
        return true;
    }

    public String getClockOutStatus() { return sharedPrefClockOut.getString(CLOCK_OUT_SHARED_PF, "-"); }

    public void clearSession() {
        sessionKey.clear();
        sessionKey.commit();
        sessionClockOut.clear();
        sessionClockOut.commit();
        sessionClockIn.clear();
        sessionClockIn.commit();
    }
}
