package com.example.babybuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    TextView forgotPasswordTxt, createAccountTxt;
    TextInputLayout phoneLayout, passwordLayout;
    TextInputEditText phoneEditText, passwordEditText;
    Button loginBtn, resetPasswordBtn;
    Dialog dialog;

    //access realtime database
    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activty);

        forgotPasswordTxt = findViewById(R.id.forgotPasswordID);
        createAccountTxt = findViewById(R.id.createAccountFromLoginTxtID);
        phoneEditText = findViewById(R.id.phoneEditTextInLoginID);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordLayout = findViewById(R.id.passwordLayoutContainer);
        loginBtn = findViewById(R.id.loginBtnID);

//        dbReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://babybuy-592-default-rtdb.firebaseio.com/");
        dbReference = FirebaseDatabase.getInstance().getReference();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validateUserData(phoneEditText);
                //validateUserData(passwordEditText);

                final String phoneText = phoneEditText.getText().toString();
                final String passwordTxt = passwordEditText.getText().toString();

                if (phoneText.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Field required", Toast.LENGTH_SHORT).show();
                }{
                    dbReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //this will check if the number already exist in my DB
                            if (snapshot.hasChild(phoneText)){
                                // since I have confirm that the number is in the firebase database
                                // now I can get the password and compare with the passwordEditTxt input
                                final String getUserPassword = snapshot.child(phoneText).child("password").getValue(String.class);

                                if (getUserPassword.equals(passwordEditText)){
                                    Toast.makeText(LoginActivity.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }else {
                                    Toast.makeText(LoginActivity.this, "Logged in not successful, Try again", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(LoginActivity.this, "incorrect inputs, Try again", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });

        forgotPasswordTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ResetPassword.class);
                startActivity(intent);
            }
        });

        createAccountTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CreateAccount.class);
                startActivity(intent);
            }
        });

        // watch when the edittext is clicked, typing or wrong character for phone EditText
//        phoneEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                String phoneNumber = charSequence.toString();
//
//                if (phoneNumber.length() >= 15){
////                    phoneLayout.setError("Not a valid phone number");
//                }else {
////                    phoneLayout.setError("");
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });




        // watch when the edittext is clicked, typing or wrong character for password EditText
//        passwordEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                String password = charSequence.toString();
//
//                if (password.length() >= 6){
//                    Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
//                    Matcher matcher = pattern.matcher(password);
//                    boolean doesPasswordContainSpecialCharacter = matcher.find();
//                    if (doesPasswordContainSpecialCharacter){
//                        passwordLayout.setError("");
//                        passwordLayout.setHelperText("");
//                    }else {
//                        passwordLayout.setHelperText("");
//                        passwordLayout.setError("Weak Password, should include minimum one special character");
//                    }
//                }else {
//                    passwordLayout.setHelperText("Enter Minimum of 6 character");
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });




        //if the user enters a wrong password and click login Button
//        View alertIncorrectPasswordDialog = LayoutInflater.from(LoginActivity.this).inflate(R.layout.incorrect_password_dialog, null);
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
//        alertDialog.setView(alertIncorrectPasswordDialog);
//        resetPasswordBtn = (Button) alertIncorrectPasswordDialog.findViewById(R.id.resetPasswordBtnID);
//
//
//        final AlertDialog dialog = alertDialog.create();
//
//        loginBtn = (Button) findViewById(R.id.loginBtnID);
//        loginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.show();
//            }
//        });

        //

//        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.cancel();
//                //showWrongPasswordDialog("login", "Forget Password", "Did you forget it", "Reset");
//            }
//        });
    }

    //this method validates if use have entered correct input
    public void validateUserData(EditText input){
        if (input.getText().toString().isEmpty()){
            input.setError("Required Field.");
        }
    }
}