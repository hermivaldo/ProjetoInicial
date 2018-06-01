package com.example.hermivaldo.projetoinicial.services

import android.content.Context
import android.os.Handler
import com.example.hermivaldo.projetoinicial.entity.Book
import com.example.hermivaldo.projetoinicial.util.DbWorkThread

class BookUtil(val context: Context, val dbWorkThread: DbWorkThread?) {

    private val dataBook: BookDATA? = BookDATA.getInstance(context)
    private val mUIHandler = Handler()

    fun insertBook(book: Book){
        val task = Runnable {

            dataBook?.BookDAO()?.insert(book)

        }
        dbWorkThread?.postTask(task)


    }

    fun deleteData(book: Book){

        val task = Runnable {
            dataBook?.BookDAO()?.delete(book)
        }

        dbWorkThread?.postTask(task)
    }

    fun getAllBooks(getListBook: (List<Book>?) -> Unit){
        val task = Runnable {

            val book = dataBook?.BookDAO()?.getAllBook()
            mUIHandler.post({
                getListBook(book)
            })

        }
        dbWorkThread?.postTask(task)
    }



}