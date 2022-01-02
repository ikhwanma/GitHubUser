package com.example.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.dataclass.Users
import com.example.githubuser.databinding.ItemRowUsersBinding

class FollowAdapter(private val listFollow:ArrayList<Users>):RecyclerView.Adapter<FollowAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUsersBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val users = listFollow[position]
        Glide.with(holder.itemView.context).load(users.avatar).into(holder.binding.imgUsers)
        holder.binding.tvName.text = users.username
    }

    override fun getItemCount(): Int = listFollow.size

    class ListViewHolder(var binding: ItemRowUsersBinding) : RecyclerView.ViewHolder(binding.root)

}