package com.example.hermivaldo.projetoinicial.services

import android.arch.persistence.room.*
import com.example.hermivaldo.projetoinicial.entity.Book


@Dao
interface BookDAO {

    @Query("select * from book")
    fun getAllBook(): List<Book>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(book: Book)

    @Delete
    fun delete(book: Book)
}