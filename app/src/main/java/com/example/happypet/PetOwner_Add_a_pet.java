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

public class PetOwner_Add_a_pet extends AppCompatActivity {

    CircleImageView pet_owner_profile_image;
    TextInputEditText PetName,DOB,cato,weight,breed,color,gender;
    Button add_pet_registerNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.petowner_activity_add_a_pet);

        PetName = findViewById(R.id.PetName);
        DOB = findViewById(R.id.DOB);
        cato = findViewById(R.id.cato);
        weight = findViewById(R.id.weight);
        breed = findViewById(R.id.breed);
        color = findViewById(R.id.color);
        gender = findViewById(R.id.gender);
        add_pet_registerNow = findViewById(R.id.add_pet_registerNow);
        pet_owner_profile_image = findViewById(R.id.pet_owner_profile_image);

        add_pet_registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String petname = PetName.getText().toString();
                String dob = DOB.getText().toString();
                String category = cato.getText().toString();
                String petweight = weight.getText().toString();
                String petbreed = breed.getText().toString();
                String petcolor = color.getText().toString();
                String petgender = gender.getText().toString();

                Intent intent = new Intent(PetOwner_Add_a_pet.this, PetOwner_Display_Pet_Details.class);
                intent.putExtra("pet_name",petname);
                intent.putExtra("dob",dob);
                intent.putExtra("category",category);
                intent.putExtra("pet_weight",petweight);
                intent.putExtra("pet_breed",petbreed);
                intent.putExtra("pet_color",petcolor);
                intent.putExtra("pet_gender",petgender);

                startActivity(intent);
                finish();
            }
        });

    }}