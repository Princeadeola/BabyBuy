package com.example.babybuy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class CreateAccount extends AppCompatActivity {
    private static final int RC_SIGN_IN = 234;
    TextView termTxt, loginTextFromCreateAccount;
    Button createAccountBtn, continueWithGoogleBtn;
    EditText fullNameTxt, emailEditTxt, phoneEditTxt, passwordEditTx, confirmPasswordEditTxt;
    Boolean isUserDataValid = false;
    FirebaseAuth firebaseAuth;

    //access realtime database
    DatabaseReference dbReference;
    private GoogleSignInClient mGoogleSignInClient;


    // in this onStart() => When the app opens, it checks if the user already signed in before 
    // and if yes, then it moves to main activity direct, no need to ask user to sign in again
    @Override
    protected void onStart() {
        super.onStart();
        
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user!=null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        termTxt = findViewById(R.id.termTxtID);
        createAccountBtn =  findViewById(R.id.createAccountBtnID);
        continueWithGoogleBtn =  findViewById(R.id.signUpWithGoogleBtnID);
        loginTextFromCreateAccount = findViewById(R.id.loginFromCreateAccountTxtID);
        dbReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://babybuy-592-default-rtdb.firebaseio.com/");

        //create account authentication starts

        fullNameTxt = findViewById(R.id.fullNameEditText);
        emailEditTxt = findViewById(R.id.EmailEditText);
        phoneEditTxt = findViewById(R.id.phoneEditTextInCreateAccountID);
        passwordEditTx = findViewById(R.id.passwordEditText);
        confirmPasswordEditTxt = findViewById(R.id.confirmPasswordEditText);
        firebaseAuth = FirebaseAuth.getInstance();
        
        request(); // this sends request to google
        
        continueWithGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

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
    
    //method to handle request to google
    private void request(){
        //Configure google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        
        //Build a googleSignInClient with the option specified by geo
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    
    private void signIn(){
        Intent sigInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(sigInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        // Result return from launching the intent from GoogleSignInApi.getSignInIntent()
        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google sign in was successful, authenticate with firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                fireBaseAuthWithGoogle(account);
            }catch (ApiException e){
                //Google sign in failed, update UI Appropriately

                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fireBaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //sign in success, update UI with the signed in user information
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }else {
                            // if sign in fails, display a message to the user
                            Toast.makeText(CreateAccount.this, "Sorry, authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}