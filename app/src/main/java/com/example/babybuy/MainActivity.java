package com.example.babybuy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    CardView individualCard, familyCard, guestCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        individualCard = findViewById(R.id.individualCardID);
        familyCard = findViewById(R.id.familyCardID);
        guestCard = findViewById(R.id.guestCardID);

        individualCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Individual Card clicked", Toast.LENGTH_SHORT).show();
            }
        });

        familyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Family Card clicked", Toast.LENGTH_SHORT).show();
            }
        });

        guestCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Guest Card clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}