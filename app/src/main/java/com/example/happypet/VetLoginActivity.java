package com.example.happypet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class VetLoginActivity extends AppCompatActivity {

    private TextView vetRegisterBtn;

    private TextInputEditText vetLoginEmail,vetLoginPassword;

    private TextView vetForgotPassword;

    private Button vetLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_login);

        vetRegisterBtn = findViewById(R.id.vetRegisterBtn);
        vetLoginEmail = findViewById(R.id.vetLoginEmail);
        vetLoginPassword = findViewById(R.id.vetLoginPassword);
        vetForgotPassword = findViewById(R.id.vetForgotPassword);
        vetLoginBtn = findViewById(R.id.vetLoginBtn);

        vetRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VetLoginActivity.this, RegisterVetActivity.class);
                startActivity(intent);
            }
        });


    }
}