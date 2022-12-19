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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccount extends AppCompatActivity {
    TextView termTxt, loginTextFromCreateAccount;
    Button createAccountBtn;
    EditText fullNameTxt, emailEditTxt, phoneEditTxt, passwordEditTx, confirmPasswordEditTxt;
    Boolean isUserDataValid = false;
    FirebaseAuth firebaseAuth;

    //access realtime database
    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        termTxt = findViewById(R.id.termTxtID);
        createAccountBtn =  findViewById(R.id.createAccountBtnID);
        loginTextFromCreateAccount = findViewById(R.id.loginFromCreateAccountTxtID);
        dbReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://babybuy-592-default-rtdb.firebaseio.com/");

        //create account authentication starts

        fullNameTxt = findViewById(R.id.fullNameEditText);
        emailEditTxt = findViewById(R.id.EmailEditText);
        phoneEditTxt = findViewById(R.id.phoneEditText);
        passwordEditTx = findViewById(R.id.passwordEditText);
        confirmPasswordEditTxt = findViewById(R.id.confirmPasswordEditText);
        firebaseAuth = FirebaseAuth.getInstance();

//        validateUserData(emailEditTxt);
//        validateUserData(phoneEditTxt);
//        validateUserData(passwordEditTx);
//        validateUserData(confirmPasswordEditTxt);
//
//        //checks if the password and confirm password are matched with each other
//        if (!passwordEditTx.getText().toString().equals(confirmPasswordEditTxt.getText().toString())){
//            isUserDataValid = false;
//            confirmPasswordEditTxt.setError("Not matched with password");
//        }else {
//            isUserDataValid = true;
//        }

//        if (isUserDataValid){
//            firebaseAuth.createUserWithEmailAndPassword(emailEditTxt.getText().toString(), passwordEditTx.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                @Override
//                public void onSuccess(AuthResult authResult) {
//                    Toast.makeText(CreateAccount.this, "Account created successfully", Toast.LENGTH_SHORT).show();
//                    Intent verifyPhoneNumberIntent = new Intent(getApplicationContext(), VerificationActivity.class);
//                    verifyPhoneNumberIntent.putExtra("number",  phoneEditTxt.getText().toString());
//                    startActivity(verifyPhoneNumberIntent);
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(CreateAccount.this, "Error ! " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUserData(fullNameTxt);
                validateUserData(emailEditTxt);
                validateUserData(phoneEditTxt);
                validateUserData(passwordEditTx);
                validateUserData(confirmPasswordEditTxt);

                String fullNameText = fullNameTxt.getText().toString();
                String emailText = emailEditTxt.getText().toString();
                String phoneText = phoneEditTxt.getText().toString();
                String passwordText = passwordEditTx.getText().toString();
                String confirmPasswordText = confirmPasswordEditTxt.getText().toString();

                //checks if the password and confirm password are matched with each other
                if (!passwordEditTx.getText().toString().equals(confirmPasswordEditTxt.getText().toString())){
                    confirmPasswordEditTxt.setError("Not matched with password");

                }else{
                    dbReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.hasChild(phoneText)){
                                Toast.makeText(CreateAccount.this, "Phone is already registered!", Toast.LENGTH_SHORT).show();
                            }else {
                                // sending data to realtime database, we are using phone number as unique identity of every year
                                // so all the other details of user comes under phone number
                                dbReference.child("users").child(phoneText).child("fullname").setValue(fullNameText);
                                dbReference.child("users").child(phoneText).child("email").setValue(emailText);
                                dbReference.child("users").child(phoneText).child("password").setValue(passwordText);

                                Toast.makeText(CreateAccount.this, "User created successfully!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                // startActivity(new Intent(CreateAccount.this, VerificationActivity.class));
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
//        String text = "By clicking Create an Account Below, you agree to BabyBuy’s Terms and Policy?";
//        SpannableString ss = new SpannableString(text);
//
//        ClickableSpan cs01 = new ClickableSpan() {
//            @Override
//            public void onClick(@NonNull View view) {
//                Toast.makeText(CreateAccount.this, "Create account Clicked", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void updateDrawState(@NonNull TextPaint ds) {
//                super.updateDrawState(ds);
//                ds.setColor(Color.RED);
//                ds.setUnderlineText(false);
//            }
//        };

//        ClickableSpan cs02 = new ClickableSpan() {
//            @Override
//            public void onClick(@NonNull View view) {
//                Toast.makeText(CreateAccount.this, "Terms Clicked", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void updateDrawState(@NonNull TextPaint ds) {
//                super.updateDrawState(ds);
//                ds.setColor(Color.RED);
//            }
//        };
//
//        ss.setSpan(cs01, 12, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        ss.setSpan(cs02, 60, 76, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        termTxt.setText(ss);
//        termTxt.setMovementMethod(LinkMovementMethod.getInstance());
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