package com.example.happypet.drivermanager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happypet.R;
import com.example.happypet.dao.Vehicle;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddVehicle extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private Button addVehicleButton;
    private TextInputEditText vehicleModel , vehicleBrand , vehicleColor , vehicleNo;

    private FirebaseUser user;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        addVehicleButton = findViewById(R.id.addVehicleBtn);

        vehicleModel = findViewById(R.id.vehicleModel);
        vehicleBrand = findViewById(R.id.vehicleBrand);
        vehicleColor = findViewById(R.id.vehicleColor);
        vehicleNo = findViewById(R.id.vehicleNo);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();



    addVehicleButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            DatabaseReference vehicleRef = ref.child("vehicles");
            final String model = vehicleModel.getText().toString().trim();
            final String brand = vehicleBrand.getText().toString().trim();
            final String color = vehicleColor.getText().toString().trim();
            final String no = vehicleNo.getText().toString().trim();

            if(TextUtils.isEmpty(model)){
                vehicleModel.setError("Vehicle Model Cannot be empty");
                return;
            }

            if(TextUtils.isEmpty(brand)){
                vehicleBrand.setError("Vehicle Brand Cannot be empty");
                return;
            }

            if(TextUtils.isEmpty(color)){
                vehicleColor.setError("Vehicle Color Cannot be empty");
                return;
            }

            if(TextUtils.isEmpty(no)){
                vehicleNo.setError("Vehicle No Cannot be empty");
                return;
            }

            try {
                Vehicle vehicle = new Vehicle(model , brand , color , no);
                DatabaseReference newVehicleRef = vehicleRef.push();
                newVehicleRef.child(userID).setValue(vehicle);
                Toast.makeText(AddVehicle.this, "Vehicle Added Successfully!!", Toast.LENGTH_SHORT).show();
            }
            catch(Exception e) {
                //  Block of code to handle errors
                Toast.makeText(AddVehicle.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
            }


        }
    });

    }
}