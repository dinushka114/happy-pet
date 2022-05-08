package com.example.happypet.drivermanager;

import android.os.Bundle;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happypet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverDashBoard extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String userID;
    private Toolbar driverToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_driver_dash_board);

        driverToolBar = findViewById(R.id.driverToolBar);
        //setContentView(R.layout.activity_register_driver);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Drivers");
        userID = user.getUid();
        System.out.println("User iddddd"+userID);
        driverToolBar.setTitle(userID);
//        setSupportActionBar(driverToolBar);

    }
}