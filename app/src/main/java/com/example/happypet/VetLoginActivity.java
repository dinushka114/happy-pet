package com.example.happypet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class VetLoginActivity extends AppCompatActivity {

    private TextView vetRegisterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_login);

        vetRegisterBtn = findViewById(R.id.vetRegisterBtn);

        vetRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VetLoginActivity.this, RegisterVetActivity.class);
                startActivity(intent);
            }
        });


    }
}