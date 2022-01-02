package com.example.githubuser.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.adapter.SectionsPagerAdapter
import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.dataclass.Users
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.viewmodel.DetailViewModel
import com.example.githubuser.viewmodel.FavoriteAddViewModel
import com.example.githubuser.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var favoriteAddViewModel: FavoriteAddViewModel
    private lateinit var users: Users
    private var isFavorite: Boolean = false
    private var isAdd: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getStringExtra(EXTRA_USER)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)

        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.title = data
        supportActionBar?.elevation = 0f

        viewModel = obtainDetailViewModel(this)

        viewModel.getUsers(data)

        viewModel.users.observe(this, {
            users = it
            setDetail()
        })

        viewModel.toastFailureMessage.observe(this,{
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.getCurrentUser(data!!).observe(this,{
            if (it != null){
                isFavorite = true
                setImageButton(isFavorite)
            }else{
                isFavorite = false
                setImageButton(isFavorite)
            }
        })



        favoriteAddViewModel = obtainViewModel(this)


        binding.btnFavorite.setOnClickListener {
            if(isFavorite){
                isAdd = false
                addFav(isAdd)
            }else{
                isAdd = true
                addFav(isAdd)
            }
        }

    }


    private fun setImageButton(cek:Boolean){
        if (cek){
            binding.btnFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext, R.drawable.ic_pink_favorite_24
                )
            )
        }else{
            binding.btnFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext, R.drawable.ic_baseline_favorite_24
                )
            )
        }
    }

    private fun addFav(cek: Boolean) {
        val username = users.username
        val avatar = users.avatar
        val id = users.id

        val favoriteUser = FavoriteUser()
        favoriteUser.let {
            it.username = username
            it.avatar = avatar
            it.id = id
        }
        if (cek) {
            favoriteAddViewModel.insert(favoriteUser)
            setImageButton(true)
        }
        else {
            favoriteAddViewModel.delete(favoriteUser)
            setImageButton(false)
        }

    }


    private fun obtainViewModel(activity: DetailActivity): FavoriteAddViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteAddViewModel::class.java]
    }

    private fun obtainDetailViewModel(activity: DetailActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }


    @SuppressLint("SetTextI18n")
    fun setDetail() {
        binding.apply {
            tvName.text = users.name
            tvUsername.text = "Username : ${users.username}"
            tvCompany.text = users.company
            tvLocation.text = users.location
            tvFollowers.text = users.jumlahFollowers
            tvFollowing.text = users.jumlahFollowing
            tvRepo.text = users.jumlahRepository
            Glide.with(this@DetailActivity).load(users.avatar).into(imgUsers)
        }
    }

    companion object {
        const val EXTRA_USER = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}