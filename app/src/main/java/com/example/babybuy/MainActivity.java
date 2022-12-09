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
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView  = findViewById(R.id.bottomNavID);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutID,homeFragment).commit();

//        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.notification);
//        badgeDrawable.setVisible(true);
//        badgeDrawable.setNumber(8);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutID,homeFragment).commit();
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