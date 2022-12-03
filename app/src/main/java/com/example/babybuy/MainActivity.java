package com.example.babybuy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babybuy.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getCurrentFragment(new HomeFragment());

//        binding.bottomNavID.setOnItemReselectedListener(item -> {
//            switch (item.getItemId()){
//                case R.id.home:
//                    getCurrentFragment(new HomeFragment());
//                    break;
//                case R.id.myList:
//                    break;
//                case R.id.assigned:
//                    break;
//                case R.id.profile:
//                    getCurrentFragment(new ProfileFragment());
//                    break;
//            }
//        });
    }

    private void getCurrentFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.frameLayoutID, fragment);
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();c

        binding.bottomNavID.setOnItemReselectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    getCurrentFragment(new HomeFragment());
                    break;
                case R.id.myList:
                    break;
                case R.id.assigned:
                    break;
                case R.id.profile:
                    getCurrentFragment(new ProfileFragment());
                    break;
            }
        });
    }
}