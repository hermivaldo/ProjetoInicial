package com.example.hermivaldo.projetoinicial.api

import com.example.hermivaldo.projetoinicial.entity.Book
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApi {

    @GET("/sc/produto/pdp/{sku}/0/0/1/")
    fun  getBookBySKU(@Path("sku") sku: String) : Call<Book>
}