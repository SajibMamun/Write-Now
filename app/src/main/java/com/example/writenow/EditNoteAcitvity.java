package com.example.writenow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class EditNoteAcitvity extends AppCompatActivity {
    com.google.android.material.textfield.TextInputEditText editTitleet,editContentet;
    Button saveeditbtnid;
    String notetitle,notecontent,noteid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note_acitvity);
        editContentet=findViewById(R.id.Editwritenoteid);
        editTitleet=findViewById(R.id.edittitleid);
        saveeditbtnid=findViewById(R.id.Editsavenowbtnid);

        if(getIntent().getExtras()!=null)
        {
            notetitle=getIntent().getStringExtra("title");
            notecontent=getIntent().getStringExtra("content");
            noteid=getIntent().getStringExtra("id");

            editTitleet.setText(notetitle);
            editContentet.setText(notecontent);

        }


    }
}