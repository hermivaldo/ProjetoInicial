package com.example.hermivaldo.projetoinicial.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.hermivaldo.projetoinicial.R
import com.example.hermivaldo.projetoinicial.`interface`.OnClickListener
import com.example.hermivaldo.projetoinicial.entity.Book

class LineBookAdap(books: List<Book>, val onClick: OnClickListener): RecyclerView.Adapter<LineBookAdap.Line>() {

    private var books: List<Book>

    init {
        this.books = books
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Line {
        return Line(LayoutInflater.from(parent!!.context).inflate(R.layout.line_book,
                parent, false))
    }

    override fun getItemCount(): Int {
        return this.books.size
    }

    fun getItem(position: Int) : Book{
        return this.books[position]
    }

    override fun onBindViewHolder(holder: Line, position: Int) {
        val book = this.books.get(position)
        holder!!.descrition.text = book.name

        holder!!.itemView.setOnClickListener({
            onClick.click(position)
        })
    }

    class Line : RecyclerView.ViewHolder{

        lateinit var descrition: TextView

        constructor(itemView: View?) : super(itemView) {
            this.descrition = itemView!!.findViewById(R.id.textLineDescription)
        }

    }
}