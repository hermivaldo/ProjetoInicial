package com.example.hermivaldo.projetoinicial.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
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


class ListFragment : Fragment() {

    lateinit var recycle: RecyclerView
    lateinit var adapter: LineBookAdap
    lateinit var bookUtil: BookUtil
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        recycle = view.findViewById(R.id.rvBookList)
        this.setupRecicleView()
        return view
    }

    fun loadThread(bookUtil: BookUtil){
        this.bookUtil = bookUtil
    }

    private fun setupRecicleView(){

        recycle.layoutManager = LinearLayoutManager(context)
        bookUtil.getAllBooks {
            if (it != null && context != null){
                this.adapter = LineBookAdap(it!!, context!!, {showDetail(it)}, {deleteLine(it)})
                recycle.adapter = adapter
                recycle.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
        }
    }

    fun showDetail(book: Book){
        Toast.makeText(context, book.name, Toast.LENGTH_LONG).show()
    }

    fun deleteLine(book: Book){
        val alertDialog = AlertDialog.Builder(context!!)
        alertDialog.setTitle("Alerta") // O Titulo da notificação
        alertDialog.setMessage("Pretende delete esse livro ?") // a mensagem ou alerta

        alertDialog.setPositiveButton("Sim", { _, _ ->
            bookUtil.deleteData(book)
            this.setupRecicleView()
        })

        alertDialog.setNegativeButton("Não", { _, _ ->
            //cancelar não precisa de uma ação porem preciso definir um conteúdo para o mesmo
            //Toast.makeText(context!!, "Não", Toast.LENGTH_LONG).show()

        })
        alertDialog.show()
    }

}
