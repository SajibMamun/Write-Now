package com.example.writenow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
    com.google.android.material.textfield.TextInputEditText emailet, passwordet;
    Button loginbtn;
    TextView signuptvbtn;
    String email, password;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passwordet = findViewById(R.id.passwordetid);
        emailet = findViewById(R.id.emailetid);
        loginbtn = findViewById(R.id.loginbuttonid);
        signuptvbtn = findViewById(R.id.signuptvid);

        mAuth = FirebaseAuth.getInstance();


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailet.getText().toString().trim();
                password = passwordet.getText().toString().trim();

                checkvalidyformailandpassword(email, password);

            }
        });


        signuptvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);


            }
        });
    }


    //Validity check for mail and password
    private void checkvalidyformailandpassword(String email, String password) {
        if (email.length() == 0) {

            emailet.setError("enter your email");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailet.setError("enter valid email");

        } else if (password.length() == 0) {
            passwordet.setError("enter your password");
        } else if (password.length() < 8) {
            passwordet.setError("enter your 8 digit password");

        } else {


            // for signing use signInWithEmailAndPassword
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                //Intent to go user account page
                        Intent intent = new Intent(MainActivity.this, UserAccount.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(MainActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    }


}