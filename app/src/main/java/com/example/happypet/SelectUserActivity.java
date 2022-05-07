package com.example.happypet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.happypet.drivermanager.LoginDriver;

public class SelectUserActivity extends AppCompatActivity {

    private Button vet_login_act_btn , driver_login_act_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        vet_login_act_btn = findViewById(R.id.vet_login_act_btn);
        driver_login_act_btn = findViewById(R.id.driver_login_act_btn);

        vet_login_act_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectUserActivity.this, VetLoginActivity.class);
                startActivity(intent);
            }
        });

        driver_login_act_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectUserActivity.this, LoginDriver.class);
                startActivity(intent);
            }
        });
    }
}