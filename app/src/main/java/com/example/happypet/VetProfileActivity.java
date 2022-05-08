package com.example.happypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class VetProfileActivity extends AppCompatActivity {

    private Toolbar vetToolBar;
    private TextView  vetFullName, vetLoginEmail, vetClinicName, vetClinicAddress, vetClinicPhone, vetClinicHrs;
    private CircleImageView vet_profile_image;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_profile);

        vetToolBar = findViewById(R.id.vetToolBar);
        setSupportActionBar(vetToolBar);
        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        vetFullName = findViewById(R.id.vetFullName);
        vetLoginEmail = findViewById(R.id.vetLoginEmail);
        vetClinicName = findViewById(R.id.vetClinicName);
        vetClinicAddress = findViewById(R.id.vetClinicAddress);
        vetClinicPhone = findViewById(R.id.vetClinicPhone);
        vetClinicHrs = findViewById(R.id.vetClinicHrs);

        vet_profile_image = findViewById(R.id.vet_profile_image);
        backButton = findViewById(R.id.backButton);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("vets")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    String fullName = snapshot.child("fullName").getValue().toString();
                    vetFullName.setText(fullName);

                    String clinicName = snapshot.child("clinicName").getValue().toString();
                    vetClinicName.setText(clinicName);

                    String clinicAddress = snapshot.child("clinicAddress").getValue().toString();
                    vetClinicAddress.setText(clinicAddress);

                    String clinicPhone = snapshot.child("clinicPhone").getValue().toString();
                    vetClinicPhone.setText(clinicPhone);

                    String clinicHrs = snapshot.child("clinicHrs").getValue().toString();
                    vetClinicHrs.setText(clinicHrs);

                    String email = snapshot.child("email").getValue().toString();
                    vetLoginEmail.setText(email);

                    String imageUrl = snapshot.child("vetProfilePictureUrl").getValue().toString();
                    Glide.with(getApplicationContext()).load(imageUrl).into(vet_profile_image);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VetProfileActivity.this, VetDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }



    }
}