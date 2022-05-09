package com.example.happypet.drivermanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.happypet.R;
import com.example.happypet.dao.Driver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DriverDashBoard extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String userID;
    private Toolbar driverToolBar;
    private TextView driverName;
    private LinearLayout addNewVehicleCard , vehicleManageCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_driver_dash_board);

        driverName = findViewById(R.id.driverName);
        addNewVehicleCard = findViewById(R.id.addNewVehicle);
        vehicleManageCard = findViewById(R.id.vehicleManageCard);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Drivers");
        userID = user.getUid();

        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Driver driverProfile = snapshot.getValue(Driver.class);
                if(driverProfile!=null){
                    String fullName = driverProfile.fullName;
                    driverName.setText("Hello "+fullName);
                    System.out.println(fullName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addNewVehicleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverDashBoard.this, AddVehicle.class);
                startActivity(intent);
            }

        });

        vehicleManageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverDashBoard.this, ManageVehicles.class);
                startActivity(intent);
            }

        });


    }
}