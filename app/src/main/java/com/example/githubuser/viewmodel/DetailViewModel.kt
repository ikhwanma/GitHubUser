package com.example.githubuser.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.dataclass.Users
import com.example.githubuser.repository.FavoriteUserRepository
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.BuildConfig
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class DetailViewModel(application: Application) : ViewModel() {
    private val _users = MutableLiveData<Users>()
    val users: LiveData<Users> = _users

    private val _toastFailureMessage = MutableLiveData<String>()
    val toastFailureMessage: LiveData<String> = _toastFailureMessage

    private val _listFollowing = MutableLiveData<ArrayList<Users>>()
    val listFollowing: LiveData<ArrayList<Users>> = _listFollowing

    private val _isLoadingFollowing = MutableLiveData<Boolean>()
    val isLoadingFollowing: LiveData<Boolean> = _isLoadingFollowing

    private val _listFollowers = MutableLiveData<ArrayList<Users>>()
    val listFollowers: LiveData<ArrayList<Users>> = _listFollowers

    private val user = Users()

    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)
    fun getCurrentUser(username:String):LiveData<FavoriteUser> = mFavoriteUserRepository.getCurrentUser(username)


    fun getUsers(username: String?) {
        _isLoadingFollowing.value = true
        val client = AsyncHttpClient()

        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        val urlUser = "https://api.github.com/users/$username"
        val urlFollowers = "https://api.github.com/users/$username/followers"
        val urlFollowing = "https://api.github.com/users/$username/following"
        val urlRepo = "https://api.github.com/users/$username/repos"
        client.get(urlUser, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val responseObject = JSONObject(result)
                    user.username = username
                    user.name = responseObject.getString("name")
                    user.company = responseObject.getString("company")
                    user.location = responseObject.getString("location")
                    user.avatar = responseObject.getString("avatar_url")
                    user.id = responseObject.getInt("id")
                    if (user.name.equals("null")) user.name = "Nama Tidak Diketahui"
                    if (user.company.equals("null")) user.company = "Company Tidak Diketahui"
                    if (user.location.equals("null")) user.location = "Lokasi Tidak Diketahui"
                    _users.value = user
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error!!.message}"
                }
                _toastFailureMessage.value = errorMessage
            }
        })
        client.get(urlFollowers, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonArray = JSONArray(result)
                    user.jumlahFollowers = jsonArray.length().toString()
                    _users.value = user
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {

            }
        })
        client.get(urlFollowing, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonArray = JSONArray(result)
                    user.jumlahFollowing = jsonArray.length().toString()
                    _users.value = user
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {

            }
        })
        client.get(urlRepo, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonArray = JSONArray(result)
                    user.jumlahRepository = jsonArray.length().toString()
                    _users.value = user
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {

            }
        })
        client.get(urlFollowing, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                _isLoadingFollowing.value = false
                val listUsers = ArrayList<Users>()
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username = jsonObject.getString("login")
                        val avatar = jsonObject.getString("avatar_url")
                        val users = Users(username, avatar)
                        listUsers.add(users)
                    }
                    _listFollowing.value = listUsers
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                _isLoadingFollowing.value = false
            }
        })
        client.get(urlFollowers, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                _isLoadingFollowing.value = false
                val listUsers = ArrayList<Users>()
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username = jsonObject.getString("login")
                        val avatar = jsonObject.getString("avatar_url")
                        val users = Users(username, avatar)
                        listUsers.add(users)
                    }
                    _listFollowers.value = listUsers
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                _isLoadingFollowing.value = true
            }

        })
    }

    companion object{
        private val TAG = DetailViewModel::class.java.simpleName
    }
}