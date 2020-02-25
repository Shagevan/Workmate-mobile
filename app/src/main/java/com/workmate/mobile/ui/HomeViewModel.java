package com.workmate.mobile.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.workmate.mobile.model.Staff;
import com.workmate.mobile.repository.StaffRepository;

public class HomeViewModel extends ViewModel {

    private StaffRepository staffRepository;
    private LiveData<Staff> staffResponseLiveData;

    public HomeViewModel() {
        staffRepository = new StaffRepository();
        this.staffResponseLiveData = staffRepository.getStaffDetail();
    }

    public LiveData<Staff> getStaffDetail() {
        return staffResponseLiveData;
    }
}
