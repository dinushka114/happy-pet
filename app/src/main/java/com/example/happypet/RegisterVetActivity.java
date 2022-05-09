package com.example.happypet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happypet.dao.Vet;
import com.example.happypet.drivermanager.RegisterDriverActivity;
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

public class RegisterVetActivity extends AppCompatActivity {

    private TextView backButton;

    private CircleImageView vet_profile_image;

    private TextInputEditText vetFullName,vetClinicName,vetClinicAddress,vetClinicPhone,vetClinicHrs,vetLoginEmail,vetLoginPassword;

    private Button vetRegisterNowBtn;

    private Uri resultUri;

    private ProgressDialog loader;

    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_register);

        backButton = findViewById(R.id.backButton);
        vetRegisterNowBtn = findViewById(R.id.vetRegisterNowBtn);

        vet_profile_image = findViewById(R.id.vet_profile_image);
        vetFullName = findViewById(R.id.vetFullName);
        vetClinicName = findViewById(R.id.vetClinicName);
        vetClinicAddress = findViewById(R.id.vetClinicAddress);
        vetClinicPhone = findViewById(R.id.vetClinicPhone);
        vetClinicHrs = findViewById(R.id.vetClinicHrs);
        vetLoginEmail = findViewById(R.id.vetLoginEmail);
        vetLoginPassword = findViewById(R.id.vetLoginPassword);
        loader = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

    //vet registration button
        vetRegisterNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fullName = vetFullName.getText().toString().trim();
                final String clinicName = vetClinicName.getText().toString().trim();
                final String clinicAddress = vetClinicAddress.getText().toString().trim();
                final String clinicPhone = vetClinicPhone.getText().toString().trim();
                final String clinicHrs = vetClinicHrs.getText().toString().trim();
                final String email = vetLoginEmail.getText().toString().trim();
                final String password = vetLoginPassword.getText().toString().trim();

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

                if(vetClinicPhone.length()!=10){
                    vetClinicPhone.setError("Enter valid phone no");
                    return;
                }

                if(TextUtils.isEmpty(clinicHrs)){
                    vetClinicHrs.setError("Clinic hours are required!");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    vetLoginEmail.setError("Email is required!");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    vetLoginPassword.setError("Password is required!");
                    return;
                }

                if(password.length()<5){
                    vetLoginPassword.setError("Password must be include minimum 6 characters");
                    return;
                }

               else{
                    loader.setMessage("Registering you...");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Vet vet = new Vet(fullName ,clinicName, clinicAddress, clinicPhone, clinicHrs , email, password);
                                FirebaseDatabase.getInstance().getReference("Vets")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(vet).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(RegisterVetActivity.this , "Vet Register successfully!!" , Toast.LENGTH_LONG).show();
                                            System.out.println("SHARE");
                                        }else{
                                            Toast.makeText(RegisterVetActivity.this , "Vet Register Failed!!" , Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                            }else{
                                Toast.makeText(RegisterVetActivity.this , "Something went wrong" , Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                        //image upload
                                if(resultUri !=null){
                                    final StorageReference filePath = FirebaseStorage.getInstance().getReference()
                                            .child("vet profile images");
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
                                            Toast.makeText(RegisterVetActivity.this, "Image upload failed!", Toast.LENGTH_SHORT).show();
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
                                                        newImageMap.put("vetProfilePictureUrl", imageUrl);

                                                        userDatabaseRef.updateChildren(newImageMap).addOnCompleteListener(new OnCompleteListener() {
                                                            @Override
                                                            public void onComplete(@NonNull Task task) {

                                                                if(task.isSuccessful()){
                                                                    Toast.makeText(RegisterVetActivity.this, "Image added successful!", Toast.LENGTH_SHORT).show();
                                                                }else{
                                                                    Toast.makeText(RegisterVetActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                                }

                                                            }
                                                        });

                                                        finish();
                                                    }
                                                });
                                            }

                                        }
                                    });

                                    Intent intent = new Intent(RegisterVetActivity.this, VetDashboardActivity.class);
                                    startActivity(intent);
                                    finish();
                                    loader.dismiss();

                                }

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


        //pick a profile image from user gallery
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