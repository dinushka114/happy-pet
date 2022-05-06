package com.example.happypet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterVetActivity extends AppCompatActivity {

    private TextView backButton;

    private CircleImageView vet_profile_image;

    private TextInputEditText vetFullName,vetClinicName,vetClinicAddress,vetClinicPhone,vetClinicHrs,vetLoginEmail,vetLoginPassword;

    private Button vetRegisterNowBtn;

    private Uri resultUri;

    private ProgressDialog loader;

    //private FirebaseAuth mAuth;
    //private DatabaseReference userDatabaseRef;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_register);

        backButton = findViewById(R.id.backButton);
        vetRegisterNowBtn = findViewById(R.id.pharamacistSignup);

    //vet registration button
        vetRegisterNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = vetLoginEmail.getText().toString().trim();
                final String password = vetLoginPassword.getText().toString().trim();
                final String fullName = vetFullName.getText().toString().trim();
                final String clinicName = vetClinicName.getText().toString().trim();
                final String clinicAddress = vetClinicAddress.getText().toString().trim();
                final String clinicPhone = vetClinicPhone.getText().toString().trim();
                final String clinicHrs = vetClinicHrs.getText().toString().trim();


                if(TextUtils.isEmpty(email)){
                    vetLoginEmail.setError("Email is required!");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    vetLoginPassword.setError("Password is required!");
                    return;
                }

                if(TextUtils.isEmpty(fullName)){
                    vetFullName.setError("Full name is required!");
                    return;
                }

                if(TextUtils.isEmpty(clinicName)){
                    vetClinicName.setError("Clinic name is required!");
                    return;
                }

                if(TextUtils.isEmpty(clinicAddress)){
                    vetClinicAddress.setError("Clinic address is required!");
                    return;
                }

                if(TextUtils.isEmpty(clinicPhone)){
                    vetClinicPhone.setError("Phone number is required!");
                    return;
                }

                if(TextUtils.isEmpty(clinicHrs)){
                    vetClinicHrs.setError("Clinic hours are required!");
                    return;
                }


               else{
                    loader.setMessage("Registering you...");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    //mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<>())

                }

            }
        });

        //Go back to the login activity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterVetActivity.this, VetLoginActivity.class);
                startActivity(intent);
            }
        });

        vet_profile_image = findViewById(R.id.pharmacist_profile_image);
        vetFullName = findViewById(R.id.pharmacistFullName);
        vetClinicName = findViewById(R.id.pharmacyName);
        vetClinicAddress = findViewById(R.id.centerAddress);
        vetClinicPhone = findViewById(R.id.pharmacistContact);
        vetClinicHrs = findViewById(R.id.vetClinicHrs);
        vetLoginEmail = findViewById(R.id.pharmacistLoginEmail);
        vetLoginPassword = findViewById(R.id.pharmacistLoginPassword);
        loader = new ProgressDialog(this);

        //mAuth = new FirebaseAuth.getInstance();


        //pick profile image from user gallery
        vet_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1 && resultCode == RESULT_OK && data !=null){
            resultUri = data.getData();
            vet_profile_image.setImageURI(resultUri);
        }
    }
}