package com.example.babybuy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
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
    Button loginBtn;
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
        loginBtn = findViewById(R.id.loginBtnID);

        forgotPasswordTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
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
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWrongPasswordDialog("login", "Forget Password", "Did you forget it", "Reset");
            }
        });
    }

    private void showWrongPasswordDialog(String login, String text01, String text02, String text03){
        Button resetPasswordBtn;
        ImageView wrongPasswordImg;
        TextView text1, text2, text3;

        resetPasswordBtn = findViewById(R.id.resetPasswordBtnID);
        wrongPasswordImg = findViewById(R.id.wrongPasswordImgID);
        text1 = findViewById(R.id.incorrectPasswordTxtID);
        text2 = findViewById(R.id.forgotPasswordTxtID);
        text3 = findViewById(R.id.resetPasswordTxtID);

        dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.incorrect_password_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

//        text1.setText(text01);
//        text2.setText(text02);
//        text3.setText(text03);

//        if (login.equals("login")){
//            wrongPasswordImg.setImageResource(R.drawable.incorrect_password_retry);
//            text1.setTextColor(getResources().getColor(R.color.light_red));
//            resetPasswordBtn.setBackgroundResource(R.drawable.blue_border_colored_bg);
//        }

//        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });

        dialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
}