package com.example.githubuser.dataclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Users(
    var username:String?=null,
    var avatar:String?=null,
    var name:String?=null,
    var company:String?=null,
    var location:String?=null,
    var jumlahFollowers:String?=null,
    var jumlahFollowing:String?=null,
    var jumlahRepository:String?=null,
    var id:Int?=0
):Parcelable
