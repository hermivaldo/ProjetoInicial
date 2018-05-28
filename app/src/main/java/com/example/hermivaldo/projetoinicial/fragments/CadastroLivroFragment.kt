package com.example.hermivaldo.projetoinicial.fragments


import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner

import com.example.hermivaldo.projetoinicial.R
import com.example.hermivaldo.projetoinicial.entity.Book
import com.example.hermivaldo.projetoinicial.services.BookUtil
import com.example.hermivaldo.projetoinicial.util.DbWorkThread
import android.provider.MediaStore
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.design.widget.FloatingActionButton
import android.widget.ImageView
import com.example.hermivaldo.projetoinicial.util.ImageConversor


class CadastroLivroFragment : Fragment() {

    lateinit var bookUtil: BookUtil
    var mThread = DbWorkThread("dbWorkerThread")
    val REQUEST_IMAGE_CAPTURE = 1
    var imageBitmap: Bitmap? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_cadastro_livro, container, false)
        val button = view.findViewById<Button>(R.id.btnCadastroLivro)
        val buttonFloat = view.findViewById<FloatingActionButton>(R.id.btnFloat)
        buttonFloat.setOnClickListener {
            this.dispatchTakePictureIntent()
        }
        bookUtil = BookUtil(context!!, mThread)
        button.setOnClickListener({
            this.cadastro(it)
        })

        mThread.start()
        return view
    }

    private fun getBook() : Book{
        var book = Book()
        book.name = getStringForView(R.id.nomeLivro)
        book.size = Integer.parseInt(getStringForView(R.id.totalPaginasLivro))
        book.type = view?.findViewById<Spinner>(R.id.categorias)?.selectedItemPosition!!
        book.year = getStringForView(R.id.anoLivro)
        book.editora = getStringForView(R.id.editoraLivro)

        if (imageBitmap == null){
            book.image = ImageConversor().convert((view?.findViewById<ImageView>
            (R.id.imageBackground)?.drawable as BitmapDrawable).bitmap )
        }else {
            book.image = ImageConversor().convert(imageBitmap!!)

        }
        return book
    }

    private fun getStringForView(ref: Int): String{
        return view?.findViewById<EditText>(ref)?.text.toString()
    }

    private fun cadastro(view: View){
        bookUtil.insertBook(getBook())
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(activity!!.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mThread.quit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val extras = data!!.extras
            imageBitmap = extras!!.get("data") as Bitmap

            view?.findViewById<ImageView>(R.id.imageBackground)?.setImageBitmap(imageBitmap)
        }
    }

}
