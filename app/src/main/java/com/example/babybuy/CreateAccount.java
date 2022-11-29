package com.example.babybuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {
    TextView termTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        termTxt = findViewById(R.id.termTxtID);
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
        };

        ss.setSpan(cs01, 12, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(cs02, 60, 76, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        termTxt.setText(ss);
        termTxt.setMovementMethod(LinkMovementMethod.getInstance());
    }
}