package com.example.babybuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccount extends AppCompatActivity {
    TextView termTxt, loginTextFromCreateAccount;
    Button createAccountBtn;
    EditText emailEditTxt, phoneEditTxt, passwordEditTx, confirmPasswordEditTxt;
    Boolean isUserDataValid = false;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        termTxt = findViewById(R.id.termTxtID);
        createAccountBtn =  findViewById(R.id.createAccountBtnID);
        loginTextFromCreateAccount = findViewById(R.id.loginFromCreateAccountTxtID);




        //create account authentication starts

        emailEditTxt = findViewById(R.id.EmailEditText);
        phoneEditTxt = findViewById(R.id.phoneEditText);
        passwordEditTx = findViewById(R.id.passwordEditText);
        confirmPasswordEditTxt = findViewById(R.id.confirmPasswordEditText);
        firebaseAuth = FirebaseAuth.getInstance();


        validateUserData(emailEditTxt);
        validateUserData(phoneEditTxt);
        validateUserData(passwordEditTx);
        validateUserData(confirmPasswordEditTxt);


        //checks if the password and confirm password are matched with each other
        if (!passwordEditTx.getText().toString().equals(confirmPasswordEditTxt.getText().toString())){
            isUserDataValid = false;
            confirmPasswordEditTxt.setError("Not matched with password");
        }else {
            isUserDataValid = true;
        }



        if (isUserDataValid){
            firebaseAuth.createUserWithEmailAndPassword(emailEditTxt.getText().toString(), passwordEditTx.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(CreateAccount.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    Intent verifyPhoneNumberIntent = new Intent(CreateAccount.this, VerificationActivity.class);
                    verifyPhoneNumberIntent.putExtra("phoneNumber", "+" + phoneEditTxt.getText().toString());
                    startActivity(verifyPhoneNumberIntent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CreateAccount.this, "Error ! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateAccount.this, VerificationActivity.class);
                startActivity(intent);
            }
        });

        // create account authentication ends






        loginTextFromCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateAccount.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // selecting part of a text and clicking on part of a text
        String text = "By clicking Create an Account Below, you agree to BabyBuy’s Terms and Policy?";
        SpannableString ss = new SpannableString(text);

        ClickableSpan cs01 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Toast.makeText(CreateAccount.this, "Create account Clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.RED);
                ds.setUnderlineText(false);
            }
        };

        ClickableSpan cs02 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Toast.makeText(CreateAccount.this, "Terms Clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.RED);
            }
        };

        ss.setSpan(cs01, 12, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(cs02, 60, 76, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        termTxt.setText(ss);
        termTxt.setMovementMethod(LinkMovementMethod.getInstance());
    }


    //this method validates if use have entered correct input
    public void validateUserData(EditText input){
        if (input.getText().toString().isEmpty()){
            isUserDataValid = false;
            input.setError("Required Field.");
        }else{
            isUserDataValid = true;
        }
    }



}