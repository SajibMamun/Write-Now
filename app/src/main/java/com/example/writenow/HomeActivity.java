package com.example.writenow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    TextView nametv;
    Button createnotebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        nametv=findViewById(R.id.nametvid);
        createnotebutton=findViewById(R.id.createnotebtn);

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference rootreference=firebaseDatabase.getReference();

        FirebaseUser currentuser= FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference nameReference=rootreference.child("Users").child(currentuser.getUid()).child("name");

        nameReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                nametv.setText("Hi, welcome "+snapshot.getValue().toString() +"!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        createnotebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this,CreateNoteActivity.class);
                startActivity(intent);
            }
        });
    }
}