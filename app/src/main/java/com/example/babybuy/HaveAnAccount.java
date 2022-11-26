package com.example.babybuy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HaveAnAccount extends AppCompatActivity {
    CardView individualCard, familyCard, guestCard;
    TextView registerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_have_an_account);

        individualCard = findViewById(R.id.individualCardID);
        familyCard = findViewById(R.id.familyCardID);
        guestCard = findViewById(R.id.guestCardID);
        registerText = findViewById(R.id.registerTextID);

        individualCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HaveAnAccount.this, "Individual Card clicked", Toast.LENGTH_SHORT).show();
            }
        });

        familyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HaveAnAccount.this, "Family Card clicked", Toast.LENGTH_SHORT).show();
            }
        });

        guestCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HaveAnAccount.this, "Guest Card clicked", Toast.LENGTH_SHORT).show();
            }
        });

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HaveAnAccount.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}