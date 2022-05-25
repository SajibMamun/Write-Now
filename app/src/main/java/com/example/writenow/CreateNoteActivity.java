package com.example.writenow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNoteActivity extends AppCompatActivity {
    com.google.android.material.textfield.TextInputEditText noteTitleet, WriteNoteet;

    Button savenotebtn;
    String title, note;
    Context mcontext;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        noteTitleet = findViewById(R.id.titleid);
        WriteNoteet = findViewById(R.id.writenoteid);
        savenotebtn = findViewById(R.id.savenowbtnid);
        mcontext = CreateNoteActivity.this;
        progressBar = findViewById(R.id.Progressbarid);

        savenotebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = noteTitleet.getText().toString().trim();
                note = WriteNoteet.getText().toString().trim();


                if (title.isEmpty()) {
                    noteTitleet.setError("enter your title");
                } else if (note.isEmpty()) {
                    WriteNoteet.setError("Write a note please");

                } else {
                    progressBar.setVisibility(view.VISIBLE);
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference rootReference = firebaseDatabase.getReference();
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                    DatabaseReference noterefernces = rootReference.child("Users").child(firebaseUser.getUid()).
                            child("Notes");

                    DatabaseReference newNotesRefrence = noterefernces.push(); //it will auto genreate id for notes
                    //newNotesRefrence.setValue("title",noteTitleet.getText().toString().trim());
                    //newNotesRefrence.setValue("Note Content",WriteNoteet.getText().toString().trim());

                    //someID:{noteTitle: "noteTitle", noteContent: "noteContent"}
                    Note newNote = new Note(title, note);
                    newNotesRefrence.setValue(newNote).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(view.INVISIBLE);
                                Toast.makeText(getApplicationContext(), "Note saved Successfully", Toast.LENGTH_SHORT).show();
                                CreateNoteActivity.this.finish();
                            } else {
                                progressBar.setVisibility(view.INVISIBLE);
                                Toast.makeText(getApplicationContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }


            }
        });


    }
}