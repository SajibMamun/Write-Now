package com.example.writenow;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    ArrayList<Note>  notedatalist;
    Context context;

    public NoteAdapter(ArrayList<Note> notedatalist, Context context) {
        this.notedatalist = notedatalist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.itemdesign,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final  Note note=notedatalist.get(position);
        holder.notetitle.setText(note.noteTitle);
        holder.noteContent.setText(note.noteContent);
        holder.Edittvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context,EditNoteAcitvity.class);
                intent.putExtra("title",note.getNoteTitle());
                intent.putExtra("content",note.getNoteContent());
                intent.putExtra("id",note.getNoteID());
                context.startActivity(intent);

            }
        });



        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder =new AlertDialog.Builder(context);
                builder.setMessage("Are you sure want to delete the note? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ((HomeActivity)context).deleteNotefromFirebase(note.getNoteID());



                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();



                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return notedatalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView notetitle,noteContent;
        ImageView Edittvbtn,deletebtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            notetitle=itemView.findViewById(R.id.itemtitleid);
            noteContent=itemView.findViewById(R.id.notecontentitemid);
            Edittvbtn=itemView.findViewById(R.id.Editbtnid);
            deletebtn=itemView.findViewById(R.id.deletebtnid);
        }
    }
}
