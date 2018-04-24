package com.example.hermivaldo.projetoinicial.fragments


import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.hermivaldo.projetoinicial.R
import com.example.hermivaldo.projetoinicial.`interface`.OnClickListener
import com.example.hermivaldo.projetoinicial.adapter.LineBookAdap
import com.example.hermivaldo.projetoinicial.entity.Book
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 *
 */
class ListFragment : Fragment() {

    lateinit var recycle: RecyclerView
    lateinit var adapter: LineBookAdap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        recycle = view.findViewById(R.id.rvBookList)
        this.setupRecicleView()

        return view
    }

    fun setupRecicleView(){
        recycle.layoutManager = LinearLayoutManager(context)
        var book = Book("aslkdj","a torre negra",1,800,Date())
        var book1 = Book("aslkdj","a coisa",1,800,Date())
        var book2 = Book("aslkdj","ultimo turno",1,800,Date())
        var book3 = Book("aslkdj","pequena abelha",1,800,Date())
        var book11 = Book("aslkdj","a coisa",1,800,Date())
        var book21 = Book("aslkdj","ultimo turno",1,800,Date())
        var book31 = Book("aslkdj","pequena abelha",1,800,Date())
        var book32 = Book("aslkdj","pequena abelha",1,800,Date())
        var book33 = Book("aslkdj","pequena abelha",1,800,Date())
        var book34 = Book("aslkdj","pequena abelha",1,800,Date())

        var books = ArrayList<Book>()

        books.add(book32)
        books.add(book33)
        books.add(book34)
        books.add(book)
        books.add(book1)
        books.add(book2)
        books.add(book3)
        books.add(book11)
        books.add(book21)
        books.add(book31)

        this.adapter = LineBookAdap(books, onClick)
        recycle.adapter = adapter
        recycle.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    var onClick = object : OnClickListener {
        override fun click(position: Int) {
            val book = adapter.getItem(position)
            Toast.makeText(context, book.name, Toast.LENGTH_LONG).show()
        }

    }


}
