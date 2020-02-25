package com.workmate.mobile.api;


import com.workmate.mobile.model.ClockResponse;
import com.workmate.mobile.model.Staff;
import com.workmate.mobile.util.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET(Constants.STAFF_REQUESTS)
    Call<Staff> getStaffRequest();

    @POST(Constants.CLOCK_IN)
    Call<ClockResponse> sendClockInRequest(@Header("Authorization") String key,
                                           @Query("latitute") String latitute,
                                           @Query("longitude") String longitude);

    @POST(Constants.CLOCK_OUT)
    Call<ClockResponse> sendClockOutRequest(@Header("Authorization") String key,
                                            @Query("latitute") String latitute,
                                            @Query("longitude") String longitude);
}
