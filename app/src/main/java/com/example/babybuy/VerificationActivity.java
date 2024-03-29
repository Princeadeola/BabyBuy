package com.example.babybuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerificationActivity extends AppCompatActivity {
    EditText otp01, otp02, otp03, otp04;
    TextView resendOtpBtn;
    Button verifyBtn;
    Boolean isOTPValid;
    FirebaseAuth firebaseAuth;
    PhoneAuthCredential phoneAuthCredential;
    PhoneAuthProvider.ForceResendingToken token;
    String verificationID;
    String userPhoneNumber;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks phoneCallBacks;

    private int resendTime = 60; // 60 seconds
    private boolean resendEnable = false; //will be true after 60 seconds
    private int selectedOTPTxtPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        otp01 = findViewById(R.id.otp01ID);
        otp02 = findViewById(R.id.otp02ID);
        otp03 = findViewById(R.id.otp03ID);
        otp04 = findViewById(R.id.otp04ID);

        resendOtpBtn = findViewById(R.id.resendOTPBtnID);
        verifyBtn = findViewById(R.id.verifyBtnID);
        firebaseAuth = FirebaseAuth.getInstance();

        Intent getUserPhoneNumberIntent = getIntent();
        userPhoneNumber = getUserPhoneNumberIntent.getStringExtra("number");

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUserData(otp01);
                validateUserData(otp02);
                validateUserData(otp03);
                validateUserData(otp04);

                String otp01text = otp01.getText().toString();
                String otp02text = otp01.getText().toString();
                String otp03text = otp01.getText().toString();
                String otp04text = otp01.getText().toString();


                if (isOTPValid){

                    String otpEntered = otp01text+otp02text+otp03text+otp04text;
                    //String otpEntered = otp01.getText().toString()+otp02.getText().toString()+otp03.getText().toString()+otp04.getText().toString();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, otpEntered);
                    VerifyUserAuthentication(credential);
                }
            }
        });


        phoneCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                verificationID = s;
                token = forceResendingToken;
                //resendOtpBtn.setEnabled(false);
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                resendOtpBtn.setEnabled(true);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                VerifyUserAuthentication(phoneAuthCredential);
                //resendOtpBtn.setEnabled(false);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(VerificationActivity.this, "Verification failed", Toast.LENGTH_SHORT).show();
            }
        };

        sendOtpToUser(userPhoneNumber);

        otp01.addTextChangedListener(textWatcher);
        otp02.addTextChangedListener(textWatcher);
        otp03.addTextChangedListener(textWatcher);
        otp04.addTextChangedListener(textWatcher);

        keyboard(otp01); //shows the keyboard on opt01 by default
//
//        startCountDownTimer(); //start countdown timer
//
        resendOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resendEnable){
                    resendOtpToUser(userPhoneNumber);
                    startCountDownTimer();
                }
            }
        });
//
//        verifyBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String otp01text = otp01.getText().toString();
//                String otp02text = otp01.getText().toString();
//                String otp03text = otp01.getText().toString();
//                String otp04text = otp01.getText().toString();
//
////                otp01.length() == 1;
//
//                final String getOTP = otp01text + otp02text + otp03text + otp04text;
//                if (getOTP.length() == 4){
//                    // this is where i will handle the verification
////                    if (!otp01text.equals("") && !otp02text.equals("") && !otp03text.equals("") && !otp04text.equals("")){
////                        otp01.setBackgroundColor(R.drawable.rounded_verify_box);
////                        otp02.setBackgroundColor(R.drawable.rounded_verify_box);
////                        otp03.setBackgroundColor(R.drawable.rounded_verify_box);
////                        otp04.setBackgroundColor(R.drawable.rounded_verify_box);
////                    }
//
//                    if (otp01.length() == 1 && otp02.length() == 1 && otp03.length() == 1 && otp04.length() == 1){
//                        otp01.setBackgroundColor(R.drawable.rounded_verify_box);
//                        otp02.setBackgroundColor(R.drawable.rounded_verify_box);
//                        otp03.setBackgroundColor(R.drawable.rounded_verify_box);
//                        otp04.setBackgroundColor(R.drawable.rounded_verify_box);
//                    }
//                }
//
//                // write logic for when the verification is correct
//                Intent intent = new Intent(VerificationActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
    }


    //
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (selectedOTPTxtPosition == 0){
                selectedOTPTxtPosition = 1; // change the position to next
                keyboard(otp02); //put keyboard on next box
            }
            else if (selectedOTPTxtPosition == 1){
                selectedOTPTxtPosition = 2; // change the position to next
                keyboard(otp03); //put keyboard on next box
            }
            else if (selectedOTPTxtPosition == 2){
                selectedOTPTxtPosition = 3; // change the position to next
                keyboard(otp04); //put keyboard on next box
            }else {
                verifyBtn.setBackgroundColor(R.drawable.rounded_button);
//                verifyBtn.setBackground(R.drawable.);
            }

        }
    };
//

    //This method handles the keyboard focusing on the input field
    private void keyboard(EditText otpEditText){
        otpEditText.requestFocus();

        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpEditText, inputMethodManager.SHOW_IMPLICIT);
    }

    private void startCountDownTimer(){
        resendEnable = true;
        resendOtpBtn.setTextColor(Color.parseColor("#99000000"));

        new CountDownTimer(resendTime * 1000, 1000){

            @Override
            public void onTick(long l) {
                resendOtpBtn.setText("Resend Code in (" + (l / 1000) + ")");
            }

            @Override
            public void onFinish() {
                resendEnable = true;
                resendOtpBtn.setText("Resend Code");
                resendOtpBtn.setTextColor(getApplicationContext().getResources().getColor(R.color.light_red));
            }
        }.start();
    }

    // this method changes the position of the keyboard after each click
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL){
            if (selectedOTPTxtPosition == 3){
                selectedOTPTxtPosition = 2; // select previous position
                keyboard(otp03); // show the keyboard on the previous position
            }
            else if (selectedOTPTxtPosition == 2){
                selectedOTPTxtPosition = 1; // select previous position
                keyboard(otp02); // show the keyboard on the previous position
            }
            else if (selectedOTPTxtPosition == 1){
                selectedOTPTxtPosition = 0; // select previous position
                keyboard(otp01); // show the keyboard on the previous position
            }

            verifyBtn.setBackgroundColor(R.drawable.inactive_rounded_button);
            return true;
        }
        else {
            return super.onKeyUp(keyCode, event);
        }
    }


    //this method validates if use have entered correct input
    public void validateUserData(EditText input){
        if (input.getText().toString().isEmpty()){
            isOTPValid = false;
            input.setError("Required Field.");
        }else{
            isOTPValid = true;
        }
    }


    // this method sends otp
    public void sendOtpToUser(String userPhoneNumber){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(userPhoneNumber, resendTime, TimeUnit.SECONDS, this, phoneCallBacks);
    }

    // this method resends the otp to the user but this time it comes with the token
    public void resendOtpToUser(String userPhoneNumber){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(userPhoneNumber, resendTime, TimeUnit.SECONDS, this, phoneCallBacks, token);
    }

    private void VerifyUserAuthentication(PhoneAuthCredential credential) {
        firebaseAuth.getCurrentUser().linkWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(VerificationActivity.this, "Account created and Linked to email", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(VerificationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}