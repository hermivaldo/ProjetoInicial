package com.example.hermivaldo.projetoinicial.fragments


import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.hermivaldo.projetoinicial.R
import com.example.hermivaldo.projetoinicial.entity.Book
import com.example.hermivaldo.projetoinicial.services.BookUtil
import android.provider.MediaStore
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TextInputLayout
import android.support.v4.content.FileProvider
import android.widget.*
import com.example.hermivaldo.projetoinicial.rules.NotNull
import com.example.hermivaldo.projetoinicial.util.ImageConversor
import io.reactivex.Observable
import ru.whalemare.rxvalidator.RxCombineValidator
import ru.whalemare.rxvalidator.RxValidator
import ru.whalemare.rxvalidator.ValidateRule
import java.io.File


class CadastroLivroFragment : Fragment(){

    lateinit var bookUtil: BookUtil
    val REQUEST_TAKE_PHOTO = 1
    var photoFile: File? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_cadastro_livro, container, false)
        val button = view.findViewById<Button>(R.id.btnCadastroLivro)
        val buttonFloat = view.findViewById<FloatingActionButton>(R.id.btnFloat)
        buttonFloat.setOnClickListener {
            this.dispatchTakePictureIntent()
        }
        button.setOnClickListener({
            this.cadastro(it)
        })
        validateForm(view)
        return view
    }

    fun loadThread(bookUtil: BookUtil){
        this.bookUtil = bookUtil
    }


    private fun getBook() : Book{

        var book = Book()
        book.name = getStringForView(R.id.nomeLivro)
        book.size = Integer.parseInt(getStringForView(R.id.totalPaginasLivro))
        book.type = view?.findViewById<Spinner>(R.id.categorias)?.selectedItemPosition!!
        book.year = getStringForView(R.id.anoLivro)
        book.editora = getStringForView(R.id.editoraLivro)

        book.image = photoFile!!.absolutePath
        return book
    }

    private fun getStringForView(ref: Int): String{
        return view?.findViewById<EditText>(ref)?.text.toString()
    }

    private fun validateForm(view: View){
        var inputName = view?.findViewById<TextInputLayout>(R.id.nomeLivro)!!
        var nomeBook: Observable<Boolean> = RxValidator(inputName).apply {
            add(NotNull())
        }.asObservable()

        RxCombineValidator(nomeBook).asObservable().distinctUntilChanged().subscribe { valid ->
            Toast.makeText(context!!, "", Toast.LENGTH_SHORT).show()
        }

    }

    private fun cadastro(view: View){
        //bookUtil.insertBook(getBook())
        //validateForm()
    }

    private fun dispatchTakePictureIntent() {
        var takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context!!.packageManager) != null) {
            // Create the File where the photo should go
            photoFile = ImageConversor().createImageFile(context!!);

            // Continue only if the File was successfully created
            if (photoFile != null) {
                var photoURI = FileProvider.getUriForFile(context!!,
                "com.example.hermivaldo.projetoinicial",
                photoFile!!);


                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            ImageConversor().setPic(view?.findViewById<ImageView>(R.id.imageBackground)!!,
                    photoFile!!.absolutePath )
        }
    }

}
