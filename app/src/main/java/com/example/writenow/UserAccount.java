package com.example.writenow;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserAccount extends AppCompatActivity {

    TextView emailtv;
    Button logoutbtn;
    Context context;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    Button Updatepassword,UpdateEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        emailtv = findViewById(R.id.userEmailTv);
        logoutbtn = findViewById(R.id.logoutbtnid);
        context = this;


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        emailtv.setText("" + user.getEmail());

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this is for signout
                // firebaseAuth.signOut();//end the session
                //finish();// end the current activity

                //we want to see it in alert Dialogue
                ShowLogoutAlertDialouge();


            }
        });

    }


    private void ShowLogoutAlertDialouge() {


        //this part for create LogoutDialogue
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure want to Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //forsignout
                        firebaseAuth.signOut();
                        ((Activity) context).finish();

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        /////////////////////////////////////////////
    }






}