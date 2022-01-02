package com.example.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.dataclass.Users
import com.example.githubuser.databinding.ItemRowUsersBinding

class UserAdapter(private val listUsers:ArrayList<Users>):RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUsersBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val users = listUsers[position]
        Glide.with(holder.itemView.context).load(users.avatar).into(holder.binding.imgUsers)
        holder.binding.tvName.text = users.username
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUsers[holder.bindingAdapterPosition])
        }
    }

    override fun getItemCount(): Int = listUsers.size

    class ListViewHolder(var binding: ItemRowUsersBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: Users)
    }
}