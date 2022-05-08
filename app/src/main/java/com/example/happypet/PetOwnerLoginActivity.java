package com.example.happypet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class PetOwnerLoginActivity extends AppCompatActivity {

    private TextView pet_ownerRegisterBtn;
    private TextInputEditText pet_ownerLoginEmail,pet_ownerLoginPassword;
    private TextView pet_ownerForgotPassword;
    private Button pet_ownerLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_owner_login);

        pet_ownerRegisterBtn = findViewById(R.id.pet_ownerRegisterBtn);
        pet_ownerLoginEmail = findViewById(R.id.pet_ownerLoginEmail);
        pet_ownerLoginPassword = findViewById(R.id.pet_ownerLoginPassword);
        pet_ownerForgotPassword = findViewById(R.id.pet_ownerForgotPassword);
        pet_ownerLoginBtn = findViewById(R.id.pet_ownerLoginBtn);

        pet_ownerRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PetOwnerLoginActivity.this, PetOwnerHomeActivity.class);
                startActivity(intent);
            }
        });
    }
}