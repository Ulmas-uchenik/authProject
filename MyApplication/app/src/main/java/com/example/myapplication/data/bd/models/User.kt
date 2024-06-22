package com.example.myapplication.data.bd.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    @PrimaryKey
    @ColumnInfo(name ="id")
    val id: Int,
    @ColumnInfo(name = "key")
    val key: String
)
