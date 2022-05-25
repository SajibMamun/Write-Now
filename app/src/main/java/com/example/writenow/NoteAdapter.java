package com.example.writenow;

import android.content.Context;
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

        holder.notetitle.setText(notedatalist.get(position).noteTitle);
        holder.noteContent.setText(notedatalist.get(position).noteContent);
        holder.Edittvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context,EditNoteAcitvity.class);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return notedatalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView notetitle,noteContent;
        ImageView Edittvbtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            notetitle=itemView.findViewById(R.id.itemtitleid);
            noteContent=itemView.findViewById(R.id.notecontentitemid);
            Edittvbtn=itemView.findViewById(R.id.Editbtnid);
        }
    }
}
