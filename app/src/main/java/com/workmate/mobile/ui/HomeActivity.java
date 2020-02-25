package com.workmate.mobile.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.workmate.mobile.R;
import com.workmate.mobile.databinding.ActivityHomeBinding;
import com.workmate.mobile.model.Staff;
import com.workmate.mobile.util.SessionManager;
import java.util.Arrays;
import java.util.List;

import static com.workmate.mobile.util.Constants.AUTH_TOKEN;
import static com.workmate.mobile.util.Constants.CLOCK_OUT_TEXT;

public class HomeActivity extends AppCompatActivity {

    private Button button;
    private ImageView btnBackground;
    private TextView clockInTV;
    private TextView clockOutTV;

    private SessionManager sessionManager;

    private HomeViewModel homeViewModel;

    private static final int LAUNCH_CLOCK_ACTIVITY = 07;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityHomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        Staff staff = new Staff();
        binding.setHomeViewModel(staff);

        clockInTV = findViewById(R.id.clockInTV);
        clockOutTV = findViewById(R.id.clockOutTV);
        btnBackground = findViewById(R.id.btn_background);
        button = findViewById(R.id.button);


        sessionManager = new SessionManager(this);
        sessionManager.setKey(AUTH_TOKEN);

        // View Model
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        homeViewModel.getStaffDetail().observe(this, staffResponse -> {
            if (staffResponse != null) {

                List<String> items = Arrays.asList(staffResponse.getWage_type().split("_"));
                staffResponse.setWage_type(items.get(0)+" "+items.get(1));
                binding.setHomeViewModel(staffResponse);
                setClockInOut();
            }
        });


        button.setOnClickListener(v -> {
            startActivityForResult(new Intent(this, ClockActivity.class),
                    LAUNCH_CLOCK_ACTIVITY);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LAUNCH_CLOCK_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                setClockInOut();
            }
        }
    }

    private void setClockInOut(){
        if(sessionManager.isClockIn()){
            clockInTV.setText(sessionManager.getClockInStatus());
            button.setText(CLOCK_OUT_TEXT);
        }
        if(sessionManager.isClockOut()){
            clockOutTV.setText(sessionManager.getClockOutStatus());
            btnBackground.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
        }
    }
}
