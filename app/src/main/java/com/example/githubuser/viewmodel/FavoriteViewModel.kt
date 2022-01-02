package com.example.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.repository.FavoriteUserRepository

class FavoriteViewModel(application: Application): ViewModel() {
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllFavoriteUser()

}