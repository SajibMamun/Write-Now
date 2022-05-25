package com.example.writenow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    com.google.android.material.textfield.TextInputEditText emailetsignup, passwordetsignup, confirmpasswordetsignup,usernameet;
   Button Signupbtn;
ProgressBar progressBar;
    String email, password, confirmpassword,name;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailetsignup = findViewById(R.id.emailetsignupid);
        passwordetsignup = findViewById(R.id.passwordetsinguid);
        confirmpasswordetsignup = findViewById(R.id.confirmpasswordetsignupid);
        Signupbtn = findViewById(R.id.SignUpButtonid);
        usernameet=findViewById(R.id.UserNameid);

        //FirebaseAuth Object.
        mAuth = FirebaseAuth.getInstance();

        progressBar=findViewById(R.id.Progressbarid);


        Signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = emailetsignup.getText().toString().trim();
                password = passwordetsignup.getText().toString().trim();
                confirmpassword = confirmpasswordetsignup.getText().toString().trim();
                name=usernameet.getText().toString().trim();



                if(name.isEmpty())
                {
                    usernameet.setError("enter your full name");
                }
                else if (email.length() == 0) {

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
                    progressBar.setVisibility(view.VISIBLE);

                    //Signup the user with this email and password
                    //For Sign Up use createUserWithEmailAndPassword
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()) {
                                progressBar.setVisibility(view.INVISIBLE);
                                //Firebaseuser object will store user details like email,password and other profile info
                                FirebaseUser user = mAuth.getCurrentUser();

                              //name save in realtime database
                                savenameinrealtimedatabase(user);
                                Toast.makeText(SignUpActivity.this, "Sign Up Done", Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(SignUpActivity.this,HomeActivity.class);
                                startActivity(intent);

                            } else {
                                progressBar.setVisibility(view.INVISIBLE);
                                Toast.makeText(SignUpActivity.this, "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }


            }
        });
    }

    private void savenameinrealtimedatabase(FirebaseUser user) {

        //Firebase Method

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();

        DatabaseReference rootReference=firebaseDatabase.getReference();
        DatabaseReference nameReference=rootReference.child("Users").child(user.getUid()).child("name");

        nameReference.setValue(name);
    }
}