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
import android.widget.SearchView
import android.widget.Toast

import com.example.hermivaldo.projetoinicial.R
import com.example.hermivaldo.projetoinicial.adapter.LineBookAdap
import com.example.hermivaldo.projetoinicial.entity.Book
import com.example.hermivaldo.projetoinicial.services.DAOUtil


class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    override fun onQueryTextSubmit(query: String?): Boolean {
        //Toast.makeText(context, query, Toast.LENGTH_SHORT).show()

        DAOUtil.getBookBySKU(query!!, {
            if(it._id != 0){
                changeFragment(it)
            }else {
                Toast.makeText(context, "SKU não encontrado", Toast.LENGTH_SHORT).show()
            }
        })
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    lateinit var recycle: RecyclerView
    lateinit var adapter: LineBookAdap
    lateinit var DAOUtil: DAOUtil

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        recycle = view.findViewById(R.id.rvBookList)
        val searchView = view.findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(this)
        this.setupRecicleView()
        return view
    }

    fun loadThread(DAOUtil: DAOUtil){
        this.DAOUtil = DAOUtil
    }

    private fun setupRecicleView(){

        recycle.layoutManager = LinearLayoutManager(context)
        DAOUtil.getAllBooks {
            if (it != null && context != null){
                this.adapter = LineBookAdap(it!!, context!!, {showDetail(it)}, {deleteLine(it)})
                recycle.adapter = adapter
                recycle.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
        }
    }


    private fun changeFragment(book: Book){
        val transaction = fragmentManager?.beginTransaction()
        var detailFragment = DetailFragment()
        var bundle = Bundle()
        detailFragment.loadThread(DAOUtil)
        bundle.putParcelable("book", book)
        detailFragment.arguments = bundle
        transaction?.addToBackStack(null)
        transaction?.replace(R.id.mainFragm, detailFragment)
        transaction?.commit()
    }

    fun showDetail(book: Book){
        changeFragment(book)
    }

    fun deleteLine(book: Book){
        val alertDialog = AlertDialog.Builder(context!!)
        alertDialog.setTitle(R.string.alert_title) // O Titulo da notificação
        alertDialog.setMessage(R.string.alert_info) // a mensagem ou alerta

        alertDialog.setPositiveButton(context!!.resources.getString(R.string.alert_accept), { _, _ ->
            DAOUtil.deleteData(book)
            this.setupRecicleView()
        })

        alertDialog.setNegativeButton(context!!.resources.getString(R.string.alert_deny), { _, _ ->
            //cancelar não precisa de uma ação porem preciso definir um conteúdo para o mesmo
        })
        alertDialog.show()
    }

}
