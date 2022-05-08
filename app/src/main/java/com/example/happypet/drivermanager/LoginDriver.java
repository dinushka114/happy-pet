package com.example.happypet.drivermanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.happypet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginDriver extends AppCompatActivity {

    private TextView driverRegisterBtn;

    private TextInputEditText driverLoginEmail,driverLoginPassword;

    private TextView driverForgetPassword;

    private Button driverLoginBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_driver);

        mAuth = FirebaseAuth.getInstance();

        driverRegisterBtn = findViewById(R.id.driverRegisterBtn);
        driverLoginBtn = findViewById(R.id.driverLoginBtn);
        driverLoginEmail = findViewById(R.id.driverLoginEmail);
        driverLoginPassword = findViewById(R.id.driverLoginPassword);


        driverRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginDriver.this, RegisterDriverActivity.class);
                startActivity(intent);

            }
        });


    }
    public void signIn(View view){
         String email = driverLoginEmail.getText().toString().trim();
         String password = driverLoginPassword.getText().toString().trim();

         if(TextUtils.isEmpty(email)){
             driverLoginEmail.setError("Email cannot be empty");
             return;
         }
        if(TextUtils.isEmpty(password)){
            driverLoginPassword.setError("Password cannot be empty");
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            driverLoginEmail.setError("Email is not valid!!");
            return;
        }

        mAuth.signInWithEmailAndPassword(email , password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(LoginDriver.this, DriverDashBoard.class);
                            startActivity(intent);

                        }else{
                            Toast.makeText(LoginDriver.this , "Login failed" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}