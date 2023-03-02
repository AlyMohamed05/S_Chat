package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.database.entity.UserInfoEntity

@Dao
interface UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserInfoEntity)

    @Query("UPDATE t_users_info SET name= :newName WHERE id = :userId")
    suspend fun updateUserName(userId: Int, newName: String)


    @Query("SELECT * FROM t_users_info WHERE id = :id")
    suspend fun getUserInfoById(id: Int): UserInfoEntity?
}