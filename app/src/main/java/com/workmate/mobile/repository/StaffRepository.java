package com.workmate.mobile.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.workmate.mobile.api.APIClient;
import com.workmate.mobile.api.ApiInterface;
import com.workmate.mobile.model.ClockResponse;
import com.workmate.mobile.model.Staff;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffRepository {

    private ApiInterface apiRequest;

    public StaffRepository() {
        apiRequest = APIClient.getClient().create(ApiInterface.class);
    }

    public LiveData<Staff> getStaffDetail() {
        final MutableLiveData<Staff> data = new MutableLiveData<>();
        apiRequest.getStaffRequest()
                .enqueue(new Callback<Staff>() {

                    @Override
                    public void onResponse(Call<Staff> call, Response<Staff> response) {

                        if (response.body() != null) {
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Staff> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }


    public LiveData<ClockResponse> sendClockInRequest(String token, String latitute, String longitude) {
        final MutableLiveData<ClockResponse> data = new MutableLiveData<>();
        apiRequest.sendClockInRequest(token,latitute,longitude)
                .enqueue(new Callback<ClockResponse>() {

                    @Override
                    public void onResponse(Call<ClockResponse> call, Response<ClockResponse> response) {
                        if (response.body() != null) {
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ClockResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }


    public LiveData<ClockResponse> sendClockOutRequest(String token, String latitute, String longitude) {
        final MutableLiveData<ClockResponse> data = new MutableLiveData<>();
        apiRequest.sendClockOutRequest(token,latitute,longitude)
                .enqueue(new Callback<ClockResponse>() {

                    @Override
                    public void onResponse(Call<ClockResponse> call, Response<ClockResponse> response) {
                        if (response.body() != null) {
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ClockResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
