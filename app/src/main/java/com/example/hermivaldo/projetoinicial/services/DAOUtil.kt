package com.example.hermivaldo.projetoinicial.services

import android.content.Context
import android.os.Handler
import android.util.Log
import com.example.hermivaldo.projetoinicial.api.BookApi
import com.example.hermivaldo.projetoinicial.entity.Book
import com.example.hermivaldo.projetoinicial.entity.User
import com.example.hermivaldo.projetoinicial.util.DbWorkThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DAOUtil(val context: Context, val dbWorkThread: DbWorkThread?) {

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

    fun insertUser(user: User){
        val task = Runnable {

            dataBook?.UserDAO()?.insert(user)

        }
        dbWorkThread?.postTask(task)

    }

    fun getUser(user: User,getUser: (User?) -> Unit){
        val task = Runnable {

            val users = dataBook?.UserDAO()?.loadUser(user.name, user.password)
            mUIHandler.post({
                getUser(users)
            })

        }
        dbWorkThread?.postTask(task)
    }

    fun getAllUsers(getListUser: (List<User>?) -> Unit){
        val task = Runnable {

            val users = dataBook?.UserDAO()?.getAllUser()
            mUIHandler.post({
                getListUser(users)
            })

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

    fun getBookBySKU(sku : String ,goToDetail: (Book) -> Unit){

        val retrofit = Retrofit.Builder().baseUrl("https://api.saraiva.com.br")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val api = retrofit.create(BookApi::class.java)

        api.getBookBySKU(sku).enqueue(object : Callback<Book> {

            override fun onFailure(call: Call<Book>?, t: Throwable?) {
                Log.e("ERROR-JSONAPI",t.toString())
            }

            override fun onResponse(call: Call<Book>?, response: Response<Book>?) {
                goToDetail(response!!.body()!!)
            }

        })
    }





}