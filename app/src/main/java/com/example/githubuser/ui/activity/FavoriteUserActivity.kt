package com.example.githubuser.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.FavoriteUserAdapter
import com.example.githubuser.databinding.ActivityFavoriteUserBinding
import com.example.githubuser.viewmodel.FavoriteViewModel
import com.example.githubuser.viewmodel.ViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUserBinding

    private lateinit var adapter: FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite Users"

        adapter = FavoriteUserAdapter()

        val favoriteViewModel = obtainViewModel(this)
        favoriteViewModel.getAllFavoriteUser().observe(this,{
            if (it != null){
                adapter.setListUser(it)
            }
        })

        binding.apply {
            rvUsers.layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = adapter
        }

    }


    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

}