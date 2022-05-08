package com.example.happypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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

public class VetLoginActivity extends AppCompatActivity {

    private TextView vetRegister_Btn;

    private TextInputEditText vetLoginEmail,vetLoginPassword;

    private TextView vetForgotPassword;

    private Button vetLoginBtn;

   private ProgressDialog loader;
   private FirebaseAuth mAuth;

   private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_login);

        mAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser vets = mAuth.getCurrentUser();
                if(vets !=null){
                    Intent intent = new Intent(VetLoginActivity.this, VetDashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };



        vetRegister_Btn = findViewById(R.id.vetRegister_Btn);
        vetLoginEmail = findViewById(R.id.vetLoginEmail);
        vetLoginPassword = findViewById(R.id.vetLoginPassword);
        vetForgotPassword = findViewById(R.id.vetForgotPassword);
        vetLoginBtn = findViewById(R.id.vetLoginBtn);

        loader = new ProgressDialog(this);



        //User directs to the vet registration page
        vetRegister_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VetLoginActivity.this, RegisterVetActivity.class);
                startActivity(intent);
            }
        });

        vetLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = vetLoginEmail.getText().toString().trim();
                final String password = vetLoginPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    vetLoginEmail.setError("Email is required!");
                }

                if (TextUtils.isEmpty(password)) {

                    vetLoginPassword.setError("Password is required!");
                }



                else{
                        loader.setMessage("Login in progress..");
                        loader.setCanceledOnTouchOutside(false);
                        loader.show();

                        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(VetLoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(VetLoginActivity.this, VetDashboardActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(VetLoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
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