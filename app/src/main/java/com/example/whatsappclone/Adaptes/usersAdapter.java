package com.example.whatsappclone.Adaptes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.whatsappclone.Activities.ChatActivity;
import com.example.whatsappclone.R;
import com.example.whatsappclone.Models.User;
import com.example.whatsappclone.databinding.SampleLayoutBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class usersAdapter extends RecyclerView.Adapter<usersAdapter.usersViewHolder>{

    Context context;
    ArrayList<User> users;

    public usersAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    public usersAdapter(){

    }


    @NonNull
    @Override
    public usersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_layout,parent,false);
        return new usersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull usersViewHolder holder, int position) {
        User user = users.get(position);

        String senderId = FirebaseAuth.getInstance().getUid();

        String senderRoom = senderId + user.getUid();

        FirebaseDatabase.getInstance().getReference()
                .child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String lastmessage = snapshot.child("lastMsg").getValue(String.class);
                            if (lastmessage != null) {
                                long lastTime = snapshot.child("lastTime").getValue(Long.class);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");

                                holder.binding.lastmessage.setText(lastmessage);
                                holder.binding.msgTime.setText(dateFormat.format(new Date(lastTime)));
                            }


                        }else {
                            holder.binding.lastmessage.setText("Tap to chat");
                            holder.binding.msgTime.setText("00.00");

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        holder.binding.username.setText(user.getName());

        Glide.with(context).load(user.getProfileImage())
                .placeholder(R.drawable.avatar)
                .into(holder.binding.profile);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("name",user.getName());
                intent.putExtra("uid",user.getUid());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class usersViewHolder extends RecyclerView.ViewHolder{

        SampleLayoutBinding binding;

        public usersViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleLayoutBinding.bind(itemView);
        }
    }
}
