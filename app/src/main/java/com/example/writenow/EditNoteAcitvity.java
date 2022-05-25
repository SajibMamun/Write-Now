package com.example.writenow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditNoteAcitvity extends AppCompatActivity {
    com.google.android.material.textfield.TextInputEditText editTitleet,editContentet;
    Button saveeditbtnid;
    String notetitle,notecontent,noteid;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note_acitvity);
        editContentet=findViewById(R.id.Editwritenoteid);
        editTitleet=findViewById(R.id.edittitleid);
        saveeditbtnid=findViewById(R.id.Editsavenowbtnid);
        progressBar=findViewById(R.id.Progressbarid);

        if(getIntent().getExtras()!=null)
        {
            notetitle=getIntent().getStringExtra("title");
            notecontent=getIntent().getStringExtra("content");
            noteid=getIntent().getStringExtra("id");

            editTitleet.setText(notetitle);
            editContentet.setText(notecontent);

        }
        saveeditbtnid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(view.VISIBLE);

                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                FirebaseUser currentuser=firebaseAuth.getCurrentUser();
                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                DatabaseReference rootReference=firebaseDatabase.getReference();
                DatabaseReference noteRefrence=rootReference.child("Users").child(currentuser.getUid()).child("Notes");
                DatabaseReference particularNoteReference=noteRefrence.child(noteid);
                particularNoteReference.child("noteContent").setValue(editContentet.getText().toString());
                particularNoteReference.child("noteTitle").setValue(editTitleet.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            progressBar.setVisibility(view.INVISIBLE);
                            Intent intent=new Intent(EditNoteAcitvity.this,HomeActivity.class);
                            startActivity(intent);
                            Toast.makeText(EditNoteAcitvity.this,"Done",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            progressBar.setVisibility(view.INVISIBLE);
                            Toast.makeText(EditNoteAcitvity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });


    }
}