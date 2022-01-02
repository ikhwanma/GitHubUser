package com.example.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.repository.FavoriteUserRepository

class FavoriteAddViewModel(application: Application): ViewModel() {
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun insert(favoriteUser: FavoriteUser){
        mFavoriteUserRepository.insert(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUser){
        mFavoriteUserRepository.delete(favoriteUser)
    }

}