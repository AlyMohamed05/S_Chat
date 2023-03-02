package com.example.core.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "t_users_info",
    indices = [
        Index(value = ["username"], unique = true)
    ]
)
data class UserInfoEntity(
    val username: String,
    val name: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int
)