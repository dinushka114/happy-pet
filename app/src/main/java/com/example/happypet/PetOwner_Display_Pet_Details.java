package com.example.happypet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class PetOwner_Display_Pet_Details extends AppCompatActivity {

    CircleImageView pet_owner_profile_image;
    TextInputEditText PetName,DOB,cato,weight,breed,color,gender;
    Button edit_btn,delete_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.petowner_activity_display_pet_details);

        //refer to textView and button
        PetName = findViewById(R.id.PetName);
        DOB = findViewById(R.id.DOB);
        cato = findViewById(R.id.cato);
        weight = findViewById(R.id.weight);
        breed = findViewById(R.id.breed);
        color = findViewById(R.id.color);
        gender = findViewById(R.id.gender);
        edit_btn = findViewById(R.id.edit_btn);
        delete_btn = findViewById(R.id.delete_btn);

        //get the data from PetOwner_Add_a_pet
        String pet_name = "Pet Name : " + getIntent().getExtras().getString("pet_name");
        String dob = "Date of Birth : " + getIntent().getExtras().getString("dob");
        String category = "Category : " + getIntent().getExtras().getString("category");
        String pet_weight = "Weight : " + getIntent().getExtras().getString("pet_weight");
        String pet_breed = "Breed : " + getIntent().getExtras().getString("pet_breed");
        String pet_color = "Color : " + getIntent().getExtras().getString("pet_color");
        String pet_gender = "Gender : " + getIntent().getExtras().getString("pet_gender");

        //set the data to textView
        PetName.setText(pet_name);
        DOB.setText(dob);
        cato.setText(category);
        weight.setText(pet_weight);
        breed.setText(pet_breed);
        color.setText(pet_color);
        gender.setText(pet_gender);

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PetOwner_Display_Pet_Details.this, PetOwner_Update_Pet_Details.class);
                startActivity(intent);
                finish();
            }
        });

    }
}