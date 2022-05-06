package com.example.happypet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

public class RegisterPetOwnerActivity extends AppCompatActivity {

    private TextView backButton;
    private CircleImageView pet_owner_profile_image;
    private TextInputEditText FullName,Phone_Number,pet_owner_address,pet_owner_LoginEmail,pet_owner_LoginPassword;
    private Button pet_ownerRegisterNowBtn;
    private Uri resultUri;
    private ProgressDialog loader;
    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pet_owner);
        backButton = findViewById(R.id.backButton);
        pet_ownerRegisterNowBtn = findViewById(R.id.pet_ownerRegisterNowBtn);

        //pet owner registration button
        pet_ownerRegisterNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fullName = FullName.getText().toString().trim();
                final String phoneNumber = Phone_Number.getText().toString().trim();
                final String address = pet_owner_address.getText().toString().trim();
                final String email = pet_owner_LoginEmail.getText().toString().trim();
                final String password = pet_owner_LoginPassword.getText().toString().trim();

                if(TextUtils.isEmpty(fullName)){
                    FullName.setError("Full name is required!");
                    return;
                }

                if(TextUtils.isEmpty(phoneNumber)){
                    Phone_Number.setError("Phone number is required!");
                    return;
                }

                if(TextUtils.isEmpty(address)){
                    pet_owner_address.setError("Address is required!");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    pet_owner_LoginEmail.setError("Email is required!");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    pet_owner_LoginPassword.setError("Password is required!");
                    return;
                }

                else{
                    loader.setMessage("Registering you...");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(!task.isSuccessful()){
                                String error = task.getException().toString();
                                Toast.makeText(RegisterPetOwnerActivity.this, "Error" + error, Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String currentUserId = mAuth.getCurrentUser().getUid();
                                userDatabaseRef = FirebaseDatabase.getInstance().getReference()
                                        .child("pet_owners").child(currentUserId);
                                HashMap ownerInfo = new HashMap();
                                ownerInfo.put("id", currentUserId);
                                ownerInfo.put("fullName", FullName);
                                ownerInfo.put("phoneNumber", Phone_Number);
                                ownerInfo.put("address", pet_owner_address);
                                ownerInfo.put("email", pet_owner_LoginEmail);
                                ownerInfo.put("password", pet_owner_LoginPassword);

                                userDatabaseRef.updateChildren(ownerInfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(RegisterPetOwnerActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(RegisterPetOwnerActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }

                                        finish();
                                    }
                                });

                                if(resultUri !=null){
                                    final StorageReference filePath = FirebaseStorage.getInstance().getReference()
                                            .child("pet owner profile images").child(currentUserId);
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
                                            Toast.makeText(RegisterPetOwnerActivity.this, "Image upload failed!", Toast.LENGTH_SHORT).show();
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
                                                        newImageMap.put("petownerProfilePictureUrl", imageUrl);

                                                        userDatabaseRef.updateChildren(newImageMap).addOnCompleteListener(new OnCompleteListener() {
                                                            @Override
                                                            public void onComplete(@NonNull Task task) {

                                                                if(task.isSuccessful()){
                                                                    Toast.makeText(RegisterPetOwnerActivity.this, "Image added successful!", Toast.LENGTH_SHORT).show();
                                                                }else{
                                                                    Toast.makeText(RegisterPetOwnerActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                                }

                                                            }
                                                        });

                                                        finish();
                                                    }
                                                });
                                            }

                                        }
                                    });

                                    Intent intent = new Intent(RegisterPetOwnerActivity.this, VetDashboardActivity.class);
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

        //Go back to the login activity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterPetOwnerActivity.this, PetOwnerLoginActivity.class);
                startActivity(intent);
            }
        });

        pet_owner_profile_image = findViewById(R.id.pet_owner_profile_image);
        FullName = findViewById(R.id.FullName);
        Phone_Number = findViewById(R.id.Phone_Number);
        pet_owner_address = findViewById(R.id.pet_owner_address);
        pet_owner_LoginEmail = findViewById(R.id.pet_owner_LoginEmail);
        pet_owner_LoginPassword = findViewById(R.id.pet_owner_LoginPassword);
        loader = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();


        //pick a profile image from user gallery
        pet_owner_profile_image.setOnClickListener(new View.OnClickListener() {
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
        if(requestCode ==1 && resultCode == Activity.RESULT_OK && data !=null){
            resultUri = data.getData();
            pet_owner_profile_image.setImageURI(resultUri);
        }
    }
}