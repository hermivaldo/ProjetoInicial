package com.example.hermivaldo.projetoinicial.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.hermivaldo.projetoinicial.R
import com.example.hermivaldo.projetoinicial.adapter.LineBookAdap
import com.example.hermivaldo.projetoinicial.entity.Book
import com.example.hermivaldo.projetoinicial.services.BookUtil
import com.example.hermivaldo.projetoinicial.util.DbWorkThread


class ListFragment : Fragment() {

    lateinit var recycle: RecyclerView
    lateinit var adapter: LineBookAdap
    lateinit var bookUtil: BookUtil
    var mThread = DbWorkThread("dbWorkerThread")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        recycle = view.findViewById(R.id.rvBookList)
        mThread.start()
        bookUtil = BookUtil(context!!, mThread)
        this.setupRecicleView()

        return view
    }

    fun setupRecicleView(){
        recycle.layoutManager = LinearLayoutManager(context)
        bookUtil.getAllBooks {
            this.adapter = LineBookAdap(it!!, context!!, {showDetail(it)}, {deleteLine(it)})
            recycle.adapter = adapter
            recycle.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

    }

    override fun onDestroy() {
        mThread.quit()
        super.onDestroy()
    }

    fun showDetail(book: Book){
        Toast.makeText(context, book.name, Toast.LENGTH_LONG).show()
    }

    fun deleteLine(book: Book){
        Toast.makeText(context, "Estou no delete ", Toast.LENGTH_SHORT).show()
    }

}
