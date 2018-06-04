package com.example.hermivaldo.projetoinicial.fragments


import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.example.hermivaldo.projetoinicial.R
import com.example.hermivaldo.projetoinicial.entity.Book
import com.example.hermivaldo.projetoinicial.services.BookUtil
import com.example.hermivaldo.projetoinicial.util.ImageConversor


class DetailFragment : Fragment() {

    var bookUtil: BookUtil? = null
    var book: Book? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var conversor = ImageConversor()
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        this.book = this.arguments?.getParcelable<Book>("book")

        view.findViewById<TextView>(R.id.nomeLivroDescrp).text = book?.name
        var ivFoto = view.findViewById<ImageView>(R.id.imageDetail)
        if (book?.image == ""){
            conversor.loadImageFromResource(ivFoto, context!!, null)
        }else{
            conversor.setPic(ivFoto, book?.image!!)
        }

        var ic_icon = view.findViewById<ImageView>(R.id.ic_icon)
        var float_button = view.findViewById<FloatingActionButton>(R.id.btnDetalhe)

        float_button.setOnClickListener {
            changeFragment()
        }
        when (book?.type) {
            1 -> conversor.loadImageFromResource(ic_icon, context!!, R.drawable.reading)
        }
        return view
    }

    private fun changeFragment(){
        val transaction = fragmentManager?.beginTransaction()
        var detailFragment = CadastroLivroFragment()
        var bundle = Bundle()
        val listFragment = ListFragment()
        listFragment.loadThread(bookUtil!!)
        detailFragment.loadThread(bookUtil!!, listFragment)
        bundle.putParcelable("book", book)
        detailFragment.arguments = bundle
        transaction?.replace(R.id.mainFragm, detailFragment)
        transaction?.commit()
    }

    fun loadThread(bookUtil: BookUtil){
        this.bookUtil = bookUtil
    }



}
