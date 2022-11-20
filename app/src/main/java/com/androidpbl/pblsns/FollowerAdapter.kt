package com.androidpbl.pblsns

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FollowerAdapter(
    private val arrayList: ArrayList<FollowerInfo>,
    private val context: Context
) :
    RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.follower_item, parent, false)
        return FollowerViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(arrayList!![position].profile)
            .into(holder.user_profile)
        holder.user_name.text = arrayList[position].name
        holder.user_message.text = arrayList[position].message
    }

    override fun getItemCount(): Int {
        return arrayList?.size ?: 0
    }

    inner class FollowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var user_profile: ImageView
        var user_name: TextView
        var user_message: TextView

        init {
            user_profile = itemView.findViewById(R.id.user_profile)
            user_name = itemView.findViewById(R.id.user_name)
            user_message = itemView.findViewById(R.id.user_message)
        }
    }
}