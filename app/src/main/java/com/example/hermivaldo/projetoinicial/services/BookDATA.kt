package com.example.hermivaldo.projetoinicial.services

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.hermivaldo.projetoinicial.entity.Book


@Database(entities = arrayOf(Book::class), version = 1)
abstract class BookDATA : RoomDatabase() {

    abstract fun BookDAO(): BookDAO

    companion object {
        private var INSTANCE: BookDATA? = null

        fun getInstance(context: Context) : BookDATA? {
            if (INSTANCE == null) {
                synchronized(BookDATA::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            BookDATA::class.java, "book.db")
                            .build()
                }
            }

            return INSTANCE
        }

        fun destroy(){
            INSTANCE = null
        }
    }

}