package com.example.writenow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    TextView nametv;
    Button createnotebutton;
    RecyclerView recyclerView;
    ArrayList<Note> arrayList;
    ProgressBar progressBar;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        nametv = findViewById(R.id.nametvid);
        createnotebutton = findViewById(R.id.createnotebtn);
        recyclerView=findViewById(R.id.recyclerviewid);
        arrayList=new ArrayList<>();
        context=this;
        progressBar=findViewById(R.id.Progressbarid);


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference rootreference = firebaseDatabase.getReference();

        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference nameReference = rootreference.child("Users").child(currentuser.getUid()).child("name");

        nameReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                nametv.setText("Hi, welcome " + snapshot.getValue().toString() + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        /////////readNotes
        readnotesfromDatabase();
        ///////////////////////////////////////


        //Recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        progressBar.setVisibility(View.VISIBLE);










        createnotebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CreateNoteActivity.class);
                startActivity(intent);
            }
        });
    }

    private void readnotesfromDatabase() {

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        DatabaseReference noteReference=firebaseDatabase.getReference().child("Users").child(firebaseUser.getUid())
                .child("Notes");
noteReference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        //here snapshot contains noteid and notes in database
        arrayList.clear();
        Note note;
        for (DataSnapshot noteSnapshot:snapshot.getChildren()){
            progressBar.setVisibility(View.INVISIBLE);

             note=noteSnapshot.getValue(Note.class); //pass that value in the constructor of Note class

            //here we can get noteid by  using Exclude in Note class
            note.setNoteID(noteSnapshot.getKey());
          //Toast.makeText(getApplicationContext(),"note: "+note.getNoteContent(),Toast.LENGTH_SHORT).show()


            //Now add note to the arraylist for recyclerview
            arrayList.add(note);


        }
        NoteAdapter noteAdapter=new NoteAdapter(arrayList,context);
        recyclerView.setAdapter(noteAdapter);






    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});


    }

    public void deleteNotefromFirebase(String noteID) {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference noteRefrence=firebaseDatabase.getReference().child("Users").child(firebaseUser.getUid()).child("Notes");
        DatabaseReference particularNote=noteRefrence.child(noteID);

        //Delete Method
        particularNote.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"Note Deleted Successfully",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),""+task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.Logoutid:
                FirebaseAuth.getInstance().signOut();
                this.finish();
                Intent logoutintent=new Intent(HomeActivity.this,MainActivity.class);
                startActivity(logoutintent);

            case R.id.ManageAccountid:
                Intent intent=new Intent(HomeActivity.this,UserAccount.class);
                startActivity(intent);


        }


        return super.onOptionsItemSelected(item);
    }
}