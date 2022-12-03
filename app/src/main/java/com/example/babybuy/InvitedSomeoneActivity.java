package com.example.babybuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InvitedSomeoneActivity extends AppCompatActivity {
    TextView createAccountTxt;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invited_someone);

        createAccountTxt = findViewById(R.id.createAccountFromLoginTxtID);
        loginBtn = findViewById(R.id.loginInviteBtnID);

        createAccountTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InvitedSomeoneActivity.this, CreateAccount.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(InvitedSomeoneActivity.this, "You Just Logged In successfully", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(InvitedSomeoneActivity.this, CreateAccount.class);
//                startActivity(intent);
            }
        });
    }
}