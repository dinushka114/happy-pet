package com.example.happypet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class VetDashboardActivity extends AppCompatActivity {

    private Button vetLogOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_dashboard);

        vetLogOutBtn = findViewById(R.id.vetLogOutBtn);

        vetLogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(VetDashboardActivity.this, VetLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}