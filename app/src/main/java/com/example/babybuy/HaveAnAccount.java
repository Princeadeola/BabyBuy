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
                Intent intent = new Intent(HaveAnAccount.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        familyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HaveAnAccount.this, InvitedSomeoneActivity.class);
                startActivity(intent);
            }
        });

        guestCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HaveAnAccount.this, MainActivity.class);
                startActivity(intent);
            }
        });

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HaveAnAccount.this, CreateAccount.class);
                startActivity(intent);
            }
        });
    }
}