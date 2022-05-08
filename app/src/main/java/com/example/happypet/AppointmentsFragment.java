package com.example.happypet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;


public class AppointmentsFragment extends Fragment {

    private TextInputEditText date, time, petName, petOwner, petOwnerPhone, reason;

    private Button vetRegisterNowBtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
}