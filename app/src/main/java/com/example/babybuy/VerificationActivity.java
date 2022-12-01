package com.example.babybuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

import com.google.android.material.internal.TextWatcherAdapter;

public class VerificationActivity extends AppCompatActivity {
    EditText otp01, otp02, otp03, otp04;
    TextView resendOtpBtn;
    Button verifyBtn;

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

        otp01.addTextChangedListener(textWatcher);
        otp02.addTextChangedListener(textWatcher);
        otp03.addTextChangedListener(textWatcher);
        otp04.addTextChangedListener(textWatcher);

        keyboard(otp01); //shows the keyboard on opt01 by default

        startCountDownTimer(); //start countdown timer

        resendOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resendEnable){
                    startCountDownTimer();
                }
            }
        });

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp01text = otp01.getText().toString();
                String otp02text = otp01.getText().toString();
                String otp03text = otp01.getText().toString();
                String otp04text = otp01.getText().toString();

                final String getOTP = otp01text + otp02text + otp03text + otp04text;
                if (getOTP.length() == 4){
                    // this is where i will handle the verification
                }
            }
        });
    }

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
            }

        }
    };

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
}