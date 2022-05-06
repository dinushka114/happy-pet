package com.example.happypet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PharmacistDashboard extends AppCompatActivity {

    private CardView prescriptionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacist_dashboard);

        prescriptionBtn = findViewById(R.id.prescriptionBtn);

        prescriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PharmacistDashboard.this, Prescription1.class);
                startActivity(intent);
            }
        });


    }
}