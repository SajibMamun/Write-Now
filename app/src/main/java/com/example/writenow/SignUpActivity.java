package com.example.writenow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    com.google.android.material.textfield.TextInputEditText emailetsignup, passwordetsignup, confirmpasswordetsignup;
    Button Signupbtn;

    String email, password, confirmpassword;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailetsignup = findViewById(R.id.emailetsignupid);
        passwordetsignup = findViewById(R.id.passwordetsinguid);
        confirmpasswordetsignup = findViewById(R.id.confirmpasswordetsignupid);
        Signupbtn = findViewById(R.id.SignUpButtonid);

        //FirebaseAuth Object.
        mAuth = FirebaseAuth.getInstance();


        Signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = emailetsignup.getText().toString().trim();
                password = passwordetsignup.getText().toString().trim();
                confirmpassword = confirmpasswordetsignup.getText().toString().trim();


                if (email.length() == 0) {

                    emailetsignup.setError("enter your email");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailetsignup.setError("enter valid email");

                } else if (password.length() == 0) {
                    passwordetsignup.setError("enter your password");
                } else if (password.length() < 8) {
                    passwordetsignup.setError("enter your 8 digit password");

                } else if (confirmpassword.length() == 0) {
                    confirmpasswordetsignup.setError("enter your password");
                } else if (confirmpassword.length() < 8) {
                    confirmpasswordetsignup.setError("enter your 8 digit password");

                } else if (!password.equals(confirmpassword)) {
                    confirmpasswordetsignup.setError("password doesn't match");

                } else {

                    //Signup the user with this email and password
                    //For Sign Up use createUserWithEmailAndPassword
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                //Firebaseuser object will store user details like email,password and other profile info
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(SignUpActivity.this, "Sign Up Done", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(SignUpActivity.this, "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }


            }
        });
    }
}