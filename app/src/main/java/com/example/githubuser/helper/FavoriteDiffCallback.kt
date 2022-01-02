package com.example.githubuser.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuser.database.FavoriteUser

class FavoriteDiffCallback(
    private val mOldFavoriteList: List<FavoriteUser>,
    private val mNewFavoriteList: List<FavoriteUser>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavoriteList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavoriteList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavoriteList[oldItemPosition].username == mNewFavoriteList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldFavoriteList[oldItemPosition]
        val newEmployee = mNewFavoriteList[newItemPosition]
        return oldEmployee.username == newEmployee.username && oldEmployee.avatar == newEmployee.avatar
    }


}