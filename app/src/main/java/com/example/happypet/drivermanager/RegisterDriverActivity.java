package com.example.happypet.drivermanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happypet.R;
import com.example.happypet.dao.Driver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterDriverActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private TextView backButton;

    private TextInputEditText driverFullName , driverNicNo , driverPhoneNo , driverEmail , driverPassword;

    private Button driverRegisterBtn;

    private Uri resultUri;

    private ProgressDialog loader;

    private DatabaseReference userDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_driver);

        mAuth = FirebaseAuth.getInstance();

        driverRegisterBtn = (Button) findViewById(R.id.driverRegisterBtn);
        driverEmail = findViewById(R.id.driverEmail);
        driverFullName = findViewById(R.id.driverFullName);
        driverNicNo = findViewById(R.id.driverNicNo);
        driverPhoneNo = findViewById(R.id.driverPhoneNo);
        driverPassword = findViewById(R.id.driverPassword);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterDriverActivity.this, LoginDriver.class);
                startActivity(intent);
            }
        });


        driverRegisterBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click

                final String fullName = driverFullName.getText().toString().trim();
                final String nic = driverNicNo.getText().toString().trim();
                final String phone = driverPhoneNo.getText().toString().trim();
                final String email = driverEmail.getText().toString().trim();
                final String password = driverPassword.getText().toString().trim();


                if(TextUtils.isEmpty(fullName)){
                    driverFullName.setError("Full Name is required");
                    return;
                }

                if(TextUtils.isEmpty(nic)){
                    driverNicNo.setError("NIC no is required");
                    return;
                }

                if(TextUtils.isEmpty(phone)){
                    driverPhoneNo.setError("Phone no is required");
                    return;
                }

//                if(TextUtils.isDigitsOnly(phone)){
//                    driverPhoneNo.setError("Enter valid phone no");
//                    return;
//                }

                if(phone.length()!=10){
                    driverPhoneNo.setError("Enter valid phone no");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    driverEmail.setError("Email is required");
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    driverEmail.setError("Email is not valid!!");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    driverPassword.setError("Password is required");
                    return;
                }

                if(password.length()<5){
                    driverPassword.setError("Password must be include minimum 6 characters");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email , password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Driver driver = new Driver(fullName ,email  ,  nic , phone , password);
                                    FirebaseDatabase.getInstance().getReference("Drivers")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(driver).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(RegisterDriverActivity.this , "Driver Register successfully!!\nnow you can login via email and password" , Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(RegisterDriverActivity.this, LoginDriver.class);
                                                startActivity(intent);
                                            }else{
                                                Toast.makeText(RegisterDriverActivity.this , "Driver Register Failed!!" , Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }else{
                                    Toast.makeText(RegisterDriverActivity.this , "Something went wrong" , Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });


    }


}