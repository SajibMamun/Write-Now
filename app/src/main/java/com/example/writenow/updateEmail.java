package com.example.writenow;

import android.os.Bundle;
import android.util.Patterns;
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

public class updateEmail extends AppCompatActivity {
    com.google.android.material.textfield.TextInputEditText oldemailet,newemailet;
    Button updateemailbuttonid;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    String oldemail,newemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);


        oldemailet=findViewById(R.id.oldEmailid);
        newemailet=findViewById(R.id.newEmailid);
        updateemailbuttonid=findViewById(R.id.UpdateEmailbuttonid);
        progressBar=findViewById(R.id.Progressbarid);
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=mAuth.getCurrentUser();


        updateemailbuttonid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                oldemail=oldemailet.getText().toString().trim();
                newemail=newemailet.getText().toString().trim();

                if (oldemail.length() == 0) {

                    oldemailet.setError("enter your email");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(oldemail).matches()) {
                    oldemailet.setError("enter valid email");

                }  else if (newemail.length() == 0) {

                    newemailet.setError("enter your email");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(newemail).matches()) {
                    newemailet.setError("enter valid email");

                }
                else if(oldemail.equals(newemail))
                {
                    newemailet.setError("same Email Can't accepted");
                }
                else
                {

                    firebaseUser.updateEmail(newemail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                progressBar.setVisibility(view.INVISIBLE);
                                Toast.makeText(getApplicationContext(), "Email updated successfully", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                progressBar.setVisibility(view.INVISIBLE);
                                Toast.makeText(getApplicationContext(), ""+task.getException(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }




            }
        });


    }
}