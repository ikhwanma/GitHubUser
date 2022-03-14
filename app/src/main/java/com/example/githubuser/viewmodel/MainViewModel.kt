package com.example.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.dataclass.Users
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainViewModel : ViewModel() {

    private val _listUsers = MutableLiveData<ArrayList<Users>>()
    val lisUsers: LiveData<ArrayList<Users>> = _listUsers

    private val _isLoadingUsers = MutableLiveData<Boolean>()
    val isLoadingUsers: LiveData<Boolean> = _isLoadingUsers

    private val _toastFailureMessage = MutableLiveData<String>()
    val toastFailureMessage: LiveData<String> = _toastFailureMessage

    fun getListUsers(){
        _isLoadingUsers.value = true
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                _isLoadingUsers.value = false
                val listUsers = ArrayList<Users>()
                val result = String(responseBody!!)
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
                    _listUsers.value = listUsers
                    Log.d(TAG, listUsers.toString())
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
                _isLoadingUsers.value = false
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error!!.message}"
                }
                _toastFailureMessage.value = errorMessage
            }
        })
    }

    fun getSearchedUsers(query:String?){
        _isLoadingUsers.value = true
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/search/users?q=$query"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                _isLoadingUsers.value = false
                val listUsers = ArrayList<Users>()
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonObject = JSONObject(result)
                    val jsonArray = jsonObject.getJSONArray("items")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username = jsonObject.getString("login")
                        val avatar = jsonObject.getString("avatar_url")
                        val users = Users(username, avatar)
                        listUsers.add(users)
                    }
                    _listUsers.value = listUsers
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
                _isLoadingUsers.value = false
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error!!.message}"
                }
                _toastFailureMessage.value = errorMessage
            }
        })
    }

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }
}