package com.example.writenow;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.writenow.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePassword extends AppCompatActivity {
    com.google.android.material.textfield.TextInputEditText newpasswordet, confirmpasswordet;
    Button updatepasswordbtn;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    String newpassword, confirmpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);


        newpasswordet = findViewById(R.id.newpasswordid);
        confirmpasswordet = findViewById(R.id.confirmpasswordid);
        updatepasswordbtn = findViewById(R.id.UpdatePasswordButtonid);
        progressBar = findViewById(R.id.Progressbarid);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=mAuth.getCurrentUser();

        updatepasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                newpassword = newpasswordet.getText().toString().trim();
                confirmpassword = confirmpasswordet.getText().toString().trim();

                if (newpassword.length() == 0) {
                    newpasswordet.setError("enter your password");
                } else if (newpassword.length() < 8) {
                    newpasswordet.setError("enter your 8 digit password");

                } else if (confirmpassword.length() == 0) {
                    confirmpasswordet.setError("enter your password");
                } else if (confirmpassword.length() < 8) {
                    confirmpasswordet.setError("enter your 8 digit password");

                } else if (!newpassword.equals(confirmpassword)) {
                    confirmpasswordet.setError("password doesn't match");
                } else {
                    progressBar.setVisibility(view.VISIBLE);
                    firebaseUser.updatePassword(confirmpassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                progressBar.setVisibility(view.INVISIBLE);
                                Toast.makeText(getApplicationContext(),"password update successfully",Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                progressBar.setVisibility(view.INVISIBLE);
                                Toast.makeText(getApplicationContext(),""+task.getException(),Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }


            }
        });

    }

}







