package com.example.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.databinding.ItemRowUsersBinding
import com.example.githubuser.helper.FavoriteDiffCallback
import com.example.githubuser.ui.activity.DetailActivity

class FavoriteUserAdapter : RecyclerView.Adapter<FavoriteUserAdapter.FavoriteUserViewHolder>() {
    private val listUser = ArrayList<FavoriteUser>()

    fun setListUser(listUser: List<FavoriteUser>) {
        val diffCallback = FavoriteDiffCallback(this.listUser, listUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listUser.clear()
        this.listUser.addAll(listUser)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val binding =
            ItemRowUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    class FavoriteUserViewHolder(private val binding: ItemRowUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteUser: FavoriteUser) {
            with(binding) {
                tvName.text = favoriteUser.username
                Glide.with(itemView.context).load(favoriteUser.avatar).into(imgUsers)
                clItem.setOnClickListener {
                    val intent = Intent(it.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_USER, favoriteUser.username)
                    it.context.startActivity(intent)
                }
            }
        }
    }

}
