package com.example.babybuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    ListFragment listFragment = new ListFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    AssignedFragment assignedFragment = new AssignedFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView  = findViewById(R.id.bottomNavID);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutID,homeFragment).commit();

        //the bottom navigation on each item selection
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutID,homeFragment).commit();
                        return true;
                    case R.id.myList:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutID, listFragment).commit();
                        return true;
                    case R.id.assigned:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutID, assignedFragment).commit();
                        return true;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutID, profileFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }
}