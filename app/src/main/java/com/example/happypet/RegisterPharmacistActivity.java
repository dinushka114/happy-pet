package com.example.happypet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterPharmacistActivity extends AppCompatActivity {

    private TextView backButton;

    private CircleImageView pharmacist_profile_image;

    private TextInputEditText pharmacistFullName,pharmacyName,centerAddress,pharmacistContact,pharmacistLoginEmail,pharmacistLoginPassword;

    private Button pharamacistSignup;

    private Uri resultUri;

    private ProgressDialog loader;

    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pharmacist);

        backButton = findViewById(R.id.backButton);
        pharamacistSignup = findViewById(R.id.pharamacistSignup);

//pharmacist registration button
        pharamacistSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = pharmacistLoginEmail.getText().toString().trim();
                final String password = pharmacistLoginPassword.getText().toString().trim();
                final String fullName = pharmacistFullName.getText().toString().trim();
                final String clinicName = pharmacyName.getText().toString().trim();
                final String clinicAddress = centerAddress.getText().toString().trim();
                final String clinicPhone = pharmacistContact.getText().toString().trim();



                if(TextUtils.isEmpty(email)){
                    pharmacistLoginEmail.setError("Email is required!");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    pharmacistLoginPassword.setError("Password is required!");
                    return;
                }

                if(TextUtils.isEmpty(fullName)){
                    pharmacistFullName.setError("Full name is required!");
                    return;
                }

                if(TextUtils.isEmpty(clinicName)){
                    pharmacyName.setError("Clinic name is required!");
                    return;
                }

                if(TextUtils.isEmpty(clinicAddress)){
                    centerAddress.setError("Clinic address is required!");
                    return;
                }

                if(TextUtils.isEmpty(clinicPhone)){
                    pharmacistContact.setError("Phone number is required!");
                    return;
                }

                else{
                    loader.setMessage("Registering you...");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    //mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<>())

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(!task.isSuccessful()){
                                String error = task.getException().toString();
                                Toast.makeText(RegisterPharmacistActivity.this, "Error" + error, Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String currentUserId = mAuth.getCurrentUser().getUid();
                                userDatabaseRef = FirebaseDatabase.getInstance().getReference()
                                        .child("pharmacist").child(currentUserId);
                                HashMap pharmacistInfo = new HashMap();
                                pharmacistInfo.put("id", currentUserId);
                                pharmacistInfo.put("fullName", fullName);
                                pharmacistInfo.put("clinicName", clinicName);
                                pharmacistInfo.put("clinicAddress", clinicAddress);
                                pharmacistInfo.put("clinicPhone", clinicPhone);
                                pharmacistInfo.put("email", email);

                                userDatabaseRef.updateChildren(pharmacistInfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(RegisterPharmacistActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(RegisterPharmacistActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }

                                        finish();
                                    }
                                });

                                if(resultUri !=null){
                                    final StorageReference filePath = FirebaseStorage.getInstance().getReference()
                                            .child("pharmacist profile images").child(currentUserId);
                                    Bitmap bitmap = null;

                                    try {
                                        bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
                                    }catch (IOException e){
                                        e.printStackTrace();
                                    }

                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
                                    byte[] data = byteArrayOutputStream.toByteArray();
                                    UploadTask uploadTask = filePath.putBytes(data);

                                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RegisterPharmacistActivity.this, "Image upload failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            if(taskSnapshot.getMetadata() !=null && taskSnapshot.getMetadata().getReference() !=null){
                                                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        String imageUrl = uri.toString();
                                                        Map newImageMap = new HashMap();
                                                        newImageMap.put("pharmacistProfilePictureUrl", imageUrl);

                                                        userDatabaseRef.updateChildren(newImageMap).addOnCompleteListener(new OnCompleteListener() {
                                                            @Override
                                                            public void onComplete(@NonNull Task task) {

                                                                if(task.isSuccessful()){
                                                                    Toast.makeText(RegisterPharmacistActivity.this, "Image added successful!", Toast.LENGTH_SHORT).show();
                                                                }else{
                                                                    Toast.makeText(RegisterPharmacistActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                                }

                                                            }
                                                        });

                                                        finish();
                                                    }
                                                });
                                            }

                                        }
                                    });

                                    Intent intent = new Intent(RegisterPharmacistActivity.this, pharmacistRegisterView.class);
                                    startActivity(intent);
                                    finish();
                                    loader.dismiss();

                                }

                            }
                        }
                    });

                }

            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterPharmacistActivity.this, PharmacistLoginActivity.class);
                startActivity(intent);
            }
        });   pharmacist_profile_image = findViewById(R.id.pharmacist_profile_image);
        pharmacistFullName = findViewById(R.id.pharmacistFullName);
        pharmacyName = findViewById(R.id.pharmacyName);
        centerAddress = findViewById(R.id.centerAddress);
        pharmacistContact = findViewById(R.id.pharmacistContact);
        pharmacistLoginEmail = findViewById(R.id.pharmacistLoginEmail);
        pharmacistLoginPassword = findViewById(R.id.pharmacistLoginPassword);
        loader = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();


        //pick profile image from user gallery
        pharmacist_profile_image.setOnClickListener(new View.OnClickListener() {
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
            pharmacist_profile_image.setImageURI(resultUri);
        }
    }
}



