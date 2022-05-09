package com.example.happypet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class PetOwner_Dash_Board extends AppCompatActivity {

    private Button petownerLogOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.petowner_activity_dash_board);

        petownerLogOutBtn = findViewById(R.id.petownerLogOutBtn);

        petownerLogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(PetOwner_Dash_Board.this, PetOwner_Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}