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
    private TextView  fullName, email, clinicName, clinicAddress, clinicPhone, clinicHrs;
    private CircleImageView vet_profile_image;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_profile);

        vetToolBar = findViewById(R.id.vetToolBar);
        setSupportActionBar(vetToolBar);
        getSupportActionBar().setTitle("MY VET PROFILE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        clinicName = findViewById(R.id.clinicName);
        clinicAddress = findViewById(R.id.clinicAddress);
        clinicPhone = findViewById(R.id.clinicPhone);
        clinicHrs = findViewById(R.id.clinicHrs);

        vet_profile_image = findViewById(R.id.vet_profile_image);
        backButton = findViewById(R.id.backButton);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Vets")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    fullName.setText(snapshot.child("fullName").getValue().toString());

                    email.setText(snapshot.child("email").getValue().toString());

                    clinicName.setText(snapshot.child("clinicName").getValue().toString());

                    clinicAddress.setText(snapshot.child("clinicAddress").getValue().toString());

                    clinicPhone.setText(snapshot.child("clinicPhone").getValue().toString());

                    clinicHrs.setText(snapshot.child("clinicHrs").getValue().toString());

                    Glide.with(getApplicationContext()).load(snapshot.child("vetProfilePictureUrl").getValue().toString()).into(vet_profile_image);


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