package com.example.hermivaldo.projetoinicial.services

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.hermivaldo.projetoinicial.entity.User

@Dao
interface UserDAO {

    @Query("SELECT * FROM user WHERE name = :user AND password  = :password")
    fun loadUser(user: String, password: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("select * from user")
    fun getAllUser(): List<User>
}