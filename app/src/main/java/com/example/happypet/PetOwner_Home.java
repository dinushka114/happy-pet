package com.example.happypet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import java.io.BufferedReader;

public class PetOwner_Home extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    VideoView videoView;
    ImageView imageView;
    Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.petowner_activity_home);

        button = findViewById(R.id.nextActivity);
        videoView = findViewById(R.id.viewVideo);
        imageView = findViewById(R.id.image);


        bottomNavigation = findViewById((R.id.bottom_navigation));

        // add menu items
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_vet));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_appointments));
        bottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.ic_pharmacy));
        bottomNavigation.add(new MeowBottomNavigation.Model(5,R.drawable.ic_driver));
        bottomNavigation.add(new MeowBottomNavigation.Model(6,R.drawable.ic_person));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //initialize fragments
                Fragment fragment = null;
                //check condition
                switch (item.getId()){
                    case 1:
                        //when id is 1
                        //initialize home fragment
                        fragment = new HomeFragment();
                        break;
                    case 2:
                        //when id is 2
                        //initialize vets fragment
                        fragment = new VetsFragment();
                        break;
                    case 3:
                        //when id is 3
                        //initialize appointments fragment
                        fragment = new AppointmentsFragment();
                        break;
                    case 4:
                        //when id is 4
                        //initialize pharmacy fragment
                        fragment = new PharmacyFragment();
                        break;
                    case 5:
                        //when id is 5
                        //initialize drivers fragment
                        fragment = new DriversFragment();
                        break;
                    case 6:
                        //when id is 6
                        //initialize user fragment
                        fragment = new UserFragment();
                        break;

                }
                //load fragment
                loadFragment (fragment);
            }
        });


        //set home fragment initially selected
        bottomNavigation.show(1, true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });




    }

    private void loadFragment(Fragment fragment) {
        //replace fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.petowner_activity_home);

    }



}