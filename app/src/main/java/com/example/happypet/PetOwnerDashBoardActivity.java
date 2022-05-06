package com.example.happypet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class PetOwnerDashBoardActivity extends AppCompatActivity {

    private Button petownerLogOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_owner_dash_board);

        petownerLogOutBtn = findViewById(R.id.petownerLogOutBtn);

        petownerLogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(PetOwnerDashBoardActivity.this, PetOwnerLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}