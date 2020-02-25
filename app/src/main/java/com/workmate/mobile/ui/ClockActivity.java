package com.workmate.mobile.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.workmate.mobile.R;
import com.workmate.mobile.repository.StaffRepository;
import com.workmate.mobile.util.SessionManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.workmate.mobile.util.Constants.CLOCKING_OUT;
import static com.workmate.mobile.util.Constants.DATE_FORMAT;
import static com.workmate.mobile.util.Constants.LATITUDE;
import static com.workmate.mobile.util.Constants.LONGITUDE;

public class ClockActivity extends AppCompatActivity {

    private SessionManager session;
    private ProgressBar progressBar;
    private TextView clockingTV;
    private TextView cancelTV;

    private StaffRepository staffRepository;

    private CountDownTimer mCountDownTimer;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);


        session = new SessionManager(this);
        staffRepository = new StaffRepository();

        progressBar = findViewById(R.id.progressBar);
        clockingTV = findViewById(R.id.clockingTV);
        cancelTV = findViewById(R.id.cancelTV);

        cancelTV.setOnClickListener(v -> {

            mCountDownTimer.cancel();
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();

        });


        if(session.isClockIn()){
            clockingTV.setText(CLOCKING_OUT);
        }
        progressBar.getProgressDrawable().setColorFilter(
                Color.parseColor("#FFFFFF"), android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setProgress(i);

        mCountDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                i++;
                progressBar.setProgress(i * 100 / (10000 / 1000));
            }

            @Override
            public void onFinish() {
                i++;
                progressBar.setProgress(100);
                if(session.isClockIn()){
                    clockOut();
                } else {
                    clockIn();
                }
            }
        };
        mCountDownTimer.start();
    }


    public void clockIn() {
        staffRepository.sendClockInRequest(session.getKey(),LATITUDE,LONGITUDE);
        session.applyClockIn(getCurrentTime());
        setResult(Activity.RESULT_OK, new Intent());
        finish();
    }


    public void clockOut() {
        staffRepository.sendClockOutRequest(session.getKey(),LATITUDE,LONGITUDE);
        session.applyClockOut(getCurrentTime());
        setResult(Activity.RESULT_OK, new Intent());
        finish();
    }

    private String getCurrentTime(){
        Date dt = new Date();
        SimpleDateFormat timeFormatter = new SimpleDateFormat(DATE_FORMAT);
        return timeFormatter.format(dt);

    }
}
