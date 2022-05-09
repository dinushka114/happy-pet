package com.example.happypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class VetDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout vetDrawerLayout;
    private Toolbar vetToolBar;
    private NavigationView vetNav_view;

    private CircleImageView vet_profile_image;
    private TextView vetFullName, vetLoginEmail;

    private DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Vets").child(
            FirebaseAuth.getInstance().getCurrentUser().getUid()
    );

    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_dashboard);

        vetToolBar = findViewById(R.id.vetToolBar);
        setSupportActionBar(vetToolBar);
        getSupportActionBar().setTitle("Veterinarian Dashboard");

        vetDrawerLayout = findViewById(R.id.vetDrawerLayout);
        vetNav_view = findViewById(R.id.vetNav_view);

        mAuth = FirebaseAuth.getInstance();

        vetNav_view.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(VetDashboardActivity.this, vetDrawerLayout,
                vetToolBar, R.string.vet_navigation_drawer_open, R.string.vet_navigation_drawer_close);
        vetDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        vet_profile_image = vetNav_view.getHeaderView(0).findViewById(R.id.vet_profile_image);
        vetFullName = vetNav_view.getHeaderView(0).findViewById(R.id.vetFullName);
        vetLoginEmail = vetNav_view.getHeaderView(0).findViewById(R.id.vetLoginEmail);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String fullName = snapshot.child("fullName").getValue().toString();
                    vetFullName.setText(fullName);

                    String email = snapshot.child("email").getValue().toString();
                    vetLoginEmail.setText(email);

                    if(snapshot.hasChild("vetProfilePictureUrl")){
                        String imageUrl = snapshot.child("vetProfilePictureUrl").getValue().toString();
                        Glide.with(getApplicationContext()).load(imageUrl).into(vet_profile_image);
                    }
                    else{
                        vet_profile_image.setImageResource(R.drawable.person);
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.vetProfile:


                Intent intent =new Intent(VetDashboardActivity.this, VetProfileActivity.class);
                startActivity(intent);


        }
        switch (item.getItemId()) {
            case R.id.vetLogOut:

                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(VetDashboardActivity.this, SelectUserActivity.class);
                startActivity(intent);
                finish();
        }
        vetDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}