package com.example.hermivaldo.projetoinicial.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hermivaldo.projetoinicial.R
import com.example.hermivaldo.projetoinicial.entity.Book
import com.example.hermivaldo.projetoinicial.util.ImageConversor
import kotlinx.android.synthetic.main.line_book.view.*

class LineBookAdap(val books: List<Book>,
                   val context: Context,
                   val listener: (Book) -> Unit,
                   val listenerDelete: (Book) -> Unit): RecyclerView.Adapter<LineBookAdap.LineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineViewHolder {
        return LineViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.line_book,
                parent, false))
    }

    override fun getItemCount(): Int {
        return this.books.size
    }

    override fun onBindViewHolder(holder: LineViewHolder, position: Int) {
        val book = this.books.get(position)
        holder.let {
            holder.bindView(book, listener, listenerDelete)
        }

    }

    class LineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var conversor = ImageConversor()
        fun bindView(book: Book,
                     listener: (Book) -> Unit,
                     listenerDelete: (Book) -> Unit) = with(itemView){
            textLineDescription.text = book.editora
            // caso imagem vazia carregar uma imagem default do sistema
            if (book.image == ""){
                conversor.loadImageFromResource(ivFoto, context, null)
            }else{
                conversor.setPic(ivFoto, book.image)
            }
            tituloLivro.text = book.name
            // adicionar o evento de delete do book
            delete.setOnClickListener{
                listenerDelete(book)
            }
            // adicionar evento de detalhe do livro
            setOnClickListener{listener(book)}
        }

    }
}