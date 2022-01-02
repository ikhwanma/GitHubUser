package com.example.githubuser.database

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteUser(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id:Int? = 0,

    @ColumnInfo(name = "username")
    var username: String? = null,

    @NonNull
    @ColumnInfo(name = "avatar")
    var avatar: String?= null
) : Parcelable
