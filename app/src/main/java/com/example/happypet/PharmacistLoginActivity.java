package com.example.happypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PharmacistLoginActivity extends AppCompatActivity {

    private TextView pharmacistRegisterBtn;

    private TextInputEditText pharmacistLoginEmail,pharmacistLoginPassword;


    private Button pharmacistLoginBtn;

    private ProgressDialog loader;
    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacist_login);

        mAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser pharmacist = mAuth.getCurrentUser();
                if(pharmacist !=null){
                    Intent intent = new Intent(PharmacistLoginActivity.this, PharmacistDashboard.class);
                    startActivity(intent);
                    finish();
                }
            }
        };


        pharmacistRegisterBtn = findViewById(R.id.pharmacistRegisterBtn);
        pharmacistLoginEmail = findViewById(R.id.pharmacistLoginEmail);
        pharmacistLoginPassword = findViewById(R.id.pharmacistLoginPassword);
        pharmacistLoginBtn = findViewById(R.id.pharmacistLogin_btn);

        loader = new ProgressDialog(this);

        //User directs to the pharmacist registration page
        pharmacistRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PharmacistLoginActivity.this, RegisterPharmacistActivity.class);
                startActivity(intent);
            }
        });

        pharmacistLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = pharmacistLoginEmail.getText().toString().trim();
                final String password = pharmacistLoginPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    pharmacistLoginEmail.setError("Email is required!");
                }
                if (TextUtils.isEmpty(password)) {

                    pharmacistLoginPassword.setError("Password is required!");
                }
                else{
                    loader.setMessage("Login in progress..");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PharmacistLoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PharmacistLoginActivity.this, VetDashboardActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(PharmacistLoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                            loader.dismiss();
                        }
                    });
                }

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