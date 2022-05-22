package com.example.writenow;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    FirebaseAuth mAuth;
    com.google.android.material.textfield.TextInputEditText forgotemailet;
    ProgressBar forgotprogressbar;
    Button forgotbtn;
    String email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgotbtn=findViewById(R.id.ResetpasswordButton);
        forgotprogressbar=findViewById(R.id.forgotProgressbarid);
        forgotemailet=findViewById(R.id.ForgotEmailetid);
        mAuth=FirebaseAuth.getInstance();


        forgotbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = forgotemailet.getText().toString().trim();

                if (email.length() == 0) {

                    forgotemailet.setError("enter your email");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    forgotemailet.setError("enter valid email");

                }
                else{
                    forgotprogressbar.setVisibility(view.VISIBLE);
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {

                                forgotprogressbar.setVisibility(view.INVISIBLE);
                                Toast.makeText(getApplicationContext(),"check your email to reset your password",Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                forgotprogressbar.setVisibility(view.INVISIBLE);
                                Toast.makeText(getApplicationContext(),""+task.getException(),Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }
            }
        });








    }
}