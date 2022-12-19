package com.example.babybuy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 234;
    TextView forgotPasswordTxt, createAccountTxt;
    TextInputLayout phoneLayout, passwordLayout;
    TextInputEditText phoneEditText, passwordEditText;
    Button loginBtn, resetPasswordBtn;
    Dialog dialog;

    //access realtime database
    DatabaseReference dbReference;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private Button continueWithGoogleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activty);

        forgotPasswordTxt = findViewById(R.id.forgotPasswordID);
        createAccountTxt = findViewById(R.id.createAccountFromLoginTxtID);
        phoneEditText = findViewById(R.id.phoneEditTextInLoginID);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordLayout = findViewById(R.id.passwordLayoutContainer);
        continueWithGoogleBtn = findViewById(R.id.continueWithGoogleBtnID);
        loginBtn = findViewById(R.id.loginBtnID);

//        request(); // this sends request to google
//
//        continueWithGoogleBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signIn();
//            }
//        });

        final TextInputLayout input = (TextInputLayout) findViewById(R.id.phoneLayoutContainer);
        input.setErrorEnabled(true);

        dbReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://babybuy-592-default-rtdb.firebaseio.com/");

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUserData(phoneEditText);
                validateUserData(passwordEditText);

                String phoneText = phoneEditText.getText().toString();
                String passwordText = passwordEditText.getText().toString();

                if (phoneText.isEmpty() || passwordText.isEmpty()){
                    Toast.makeText(LoginActivity.this, "All field required", Toast.LENGTH_SHORT).show();
                }else {
                    dbReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //this will check if the number already exist in my DB
                            if (snapshot.hasChild(phoneText)){
                                // since I have confirm that the number is in the firebase database
                                // now I can get the password and compare with the passwordEditTxt input
                                final String getUserPassword = snapshot.child(phoneText).child("password").getValue(String.class);

                                if (getUserPassword.equals(passwordText)){
                                    Toast.makeText(LoginActivity.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }else {
                                    Toast.makeText(LoginActivity.this, "Logged in not successful, Try again", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                setErrorTextColor(input, ContextCompat.getColor(getApplicationContext(), android.R.color.darker_gray));
                                input.setError("Please enter a complete number and include country code without(+) !");
                                Toast.makeText(LoginActivity.this, "phone number not registered, Try again", Toast.LENGTH_SHORT).show();
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


    public void setErrorTextColor(TextInputLayout textInputLayout, int color) {
        try {
            Field fErrorView = TextInputLayout.class.getDeclaredField("mErrorView");
            fErrorView.setAccessible(true);
            TextView mErrorView = (TextView) fErrorView.get(textInputLayout);
            @SuppressLint("SoonBlockedPrivateApi") Field fCurTextColor = TextView.class.getDeclaredField("mCurTextColor");
            fCurTextColor.setAccessible(true);
            fCurTextColor.set(mErrorView, color);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    //method to handle request to google
//    private void request(){
//        //Configure google sign in
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//
//        //Build a googleSignInClient with the option specified by geo
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//    }
//
//    private void signIn(){
//        Intent sigInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(sigInIntent, RC_SIGN_IN);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result return from launching the intent from GoogleSignInApi.getSignInIntent()
//        if (requestCode == RC_SIGN_IN){
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                //Google sign in was successful, authenticate with firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                fireBaseAuthWithGoogle(account);
//            }catch (ApiException e){
//                //Google sign in failed, update UI Appropriately
//
//                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private void fireBaseAuthWithGoogle(GoogleSignInAccount account) {
//        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
//        firebaseAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()){
//                            //sign in success, update UI with the signed in user information
//                            FirebaseUser user = firebaseAuth.getCurrentUser();
//                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                            startActivity(intent);
//                        }else {
//                            // if sign in fails, display a message to the user
//                            Toast.makeText(LoginActivity.this, "Sorry, authentication failed", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
}