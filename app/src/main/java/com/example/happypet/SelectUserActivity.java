package com.example.happypet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectUserActivity extends AppCompatActivity {

    private Button vet_login_act_btn;
    private Button pet_owner_login_act_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        vet_login_act_btn = findViewById(R.id.vet_login_act_btn);

        vet_login_act_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectUserActivity.this, VetLoginActivity.class);
                startActivity(intent);
            }
        });

        pet_owner_login_act_btn = findViewById(R.id.pet_owner_login_act_btn);

        pet_owner_login_act_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectUserActivity.this, PetOwnerLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}