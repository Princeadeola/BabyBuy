package com.example.babybuy;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    TextView forgotPasswordTxt, createAccountTxt;
    TextInputLayout phoneLayout, passwordLayout;
    TextInputEditText phoneEditText, passwordEditText;
    Button loginBtn, resetPasswordBtn;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activty);

        forgotPasswordTxt = findViewById(R.id.forgotPasswordID);
        createAccountTxt = findViewById(R.id.createAccountFromLoginTxtID);
        phoneEditText = findViewById(R.id.phoneEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordLayout = findViewById(R.id.passwordLayoutContainer);

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
        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String phoneNumber = charSequence.toString();

                if (phoneNumber.length() >= 15){
//                    phoneLayout.setError("Not a valid phone number");
                }else {
//                    phoneLayout.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




        // watch when the edittext is clicked, typing or wrong character for password EditText
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = charSequence.toString();

                if (password.length() >= 6){
                    Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
                    Matcher matcher = pattern.matcher(password);
                    boolean doesPasswordContainSpecialCharacter = matcher.find();
                    if (doesPasswordContainSpecialCharacter){
                        passwordLayout.setError("");
                        passwordLayout.setHelperText("");
                    }else {
                        passwordLayout.setHelperText("");
                        passwordLayout.setError("Weak Password, should include minimum one special character");
                    }
                }else {
                    passwordLayout.setHelperText("Enter Minimum of 6 character");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




        //if the user enters a wrong password and click login Button
        View alertIncorrectPasswordDialog = LayoutInflater.from(LoginActivity.this).inflate(R.layout.incorrect_password_dialog, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
        alertDialog.setView(alertIncorrectPasswordDialog);
        resetPasswordBtn = (Button) alertIncorrectPasswordDialog.findViewById(R.id.resetPasswordBtnID);


        final AlertDialog dialog = alertDialog.create();

        loginBtn = (Button) findViewById(R.id.loginBtnID);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        //

        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                //showWrongPasswordDialog("login", "Forget Password", "Did you forget it", "Reset");
            }
        });

        //
    }
}