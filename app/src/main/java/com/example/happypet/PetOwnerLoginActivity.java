package com.example.happypet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PetOwnerLoginActivity extends AppCompatActivity {

    private TextView pet_ownerRegisterBtn;
    private TextInputEditText pet_ownerLoginEmail,pet_ownerLoginPassword;
    private TextView pet_ownerForgotPassword;
    private Button pet_ownerLoginBtn;

    private ProgressDialog loader;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_owner_login);

        mAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser vets = mAuth.getCurrentUser();
                if(vets !=null){
                    Intent intent = new Intent(PetOwnerLoginActivity.this, PetOwnerDashBoardActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        pet_ownerRegisterBtn = findViewById(R.id.pet_ownerRegisterBtn);
        pet_ownerLoginEmail = findViewById(R.id.pet_ownerLoginEmail);
        pet_ownerLoginPassword = findViewById(R.id.pet_ownerLoginPassword);
        pet_ownerForgotPassword = findViewById(R.id.pet_ownerForgotPassword);
        pet_ownerLoginBtn = findViewById(R.id.pet_ownerLoginBtn);

        loader = new ProgressDialog(this);

        //User directs to the pet owner registration page
        pet_ownerRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PetOwnerLoginActivity.this, RegisterPetOwnerActivity.class);
                startActivity(intent);
            }
        });

        pet_ownerRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = pet_ownerLoginEmail.getText().toString().trim();
                final String password = pet_ownerLoginPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    pet_ownerLoginEmail.setError("Email is required!");
                }
                if (TextUtils.isEmpty(password)) {

                    pet_ownerLoginPassword.setError("Password is required!");
                }
                else{
                    loader.setMessage("Login in progress..");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PetOwnerLoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PetOwnerLoginActivity.this, PetOwnerDashBoardActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(PetOwnerLoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                            loader.dismiss();
                        }
                    });
                }

            }
        });
        pet_ownerRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PetOwnerLoginActivity.this, RegisterPetOwnerActivity.class);
                startActivity(intent);
            }
        });

        pet_ownerLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PetOwnerLoginActivity.this, PetOwnerHomeActivity.class);
                startActivity(intent);
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }
}