package com.androidpbl.pblsns;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>{
    private ArrayList<FollowerInfo> arrayList;
    private Context context;

    public FollowerAdapter(ArrayList<FollowerInfo> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public FollowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.follower_item, parent, false);
        FollowerViewHolder holder = new FollowerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FollowerViewHolder holder, int position) {
        Glide.with(holder.itemView)
            .load(arrayList.get(position).getProfile())
            .into(holder.user_profile);
        holder.user_name.setText(arrayList.get(position).getName());
        holder.user_message.setText(arrayList.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return (arrayList!=null ? arrayList.size():0);
    }

    public class FollowerViewHolder extends RecyclerView.ViewHolder {
        ImageView user_profile;
        TextView user_name;
        TextView user_message;

        public FollowerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.user_profile=itemView.findViewById(R.id.user_profile);
            this.user_name=itemView.findViewById(R.id.user_name);
            this.user_message=itemView.findViewById(R.id.user_message);
        }
    }
}

