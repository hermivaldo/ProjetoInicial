package com.example.hermivaldo.projetoinicial.fragments


import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.hermivaldo.projetoinicial.R
import com.example.hermivaldo.projetoinicial.entity.Book
import com.example.hermivaldo.projetoinicial.services.DAOUtil
import android.provider.MediaStore
import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TextInputLayout
import android.support.v4.content.FileProvider
import android.widget.*
import com.example.hermivaldo.projetoinicial.rules.Rules
import com.example.hermivaldo.projetoinicial.util.ImageConversor
import io.reactivex.Observable
import ru.whalemare.rxvalidator.RxCombineValidator
import ru.whalemare.rxvalidator.RxValidator
import java.io.File


class CadastroLivroFragment : Fragment(){

    lateinit var DAOUtil: DAOUtil
    val REQUEST_TAKE_PHOTO = 1
    var photoFile: File? = null
    var changeFor:ListFragment? = null
    var mybook: Book? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // carregamento dos conteúdos da view.
        val view = inflater.inflate(R.layout.fragment_cadastro_livro, container, false)
        val button = view.findViewById<Button>(R.id.btnCadastroLivro)
        val buttonFloat = view.findViewById<FloatingActionButton>(R.id.btnFloat)


        // pegar argumento da página
        if (this.arguments != null){
            this.mybook = this.arguments?.getParcelable<Book>("book")
            loadContentForBundle(view)
        }



        // atribuição dos evento de captura de foto
        buttonFloat.setOnClickListener {
            this.dispatchTakePictureIntent()
        }

        // atribuição do evento do botão de cadastro
        button.setOnClickListener({
            this.cadastro(it)
        })

        // inserir eventos de validação do formulário.
        validateForm(view)
        return view
    }

    // método responsável por carregar a thread e o fragment de retorno
    fun loadThread(DAOUtil: DAOUtil, fragment: ListFragment){
        this.DAOUtil = DAOUtil
        this.changeFor = fragment

    }

    private fun loadContentForBundle(view: View){

        var nomeBook = view?.findViewById<TextInputLayout>(R.id.nomeLivro)?.editText
        nomeBook?.setText(this.mybook?.name)

        var sizeBook = view?.findViewById<TextInputLayout>(R.id.totalPaginasLivro).editText
        sizeBook?.setText(this.mybook?.size.toString())

        var typeBook = view?.findViewById<Spinner>(R.id.categorias)
        typeBook.setSelection(this.mybook?.type!!)

        var yearBook = view?.findViewById<TextInputLayout>(R.id.anoLivro).editText
        yearBook?.setText(this.mybook?.year)

        var editoraBook = view?.findViewById<TextInputLayout>(R.id.editoraLivro).editText
        editoraBook?.setText(this.mybook?.editora)

        if (this.mybook?.image != ""){
            var imageViewBook = view?.findViewById<ImageView>(R.id.imageBackground)
            ImageConversor().setPic(imageViewBook, this.mybook?.image!!)
        }

        val button = view.findViewById<Button>(R.id.btnCadastroLivro)
        button.setText("Editar")

        // como já está valido o formulário pois foi inserido da maneira correta da primeira vez, já
        // preciso deixar o botão ativo, pois caso contrário apenas quando a regra for invalidada e
        // revalidada que o botão torna a ficar ativo
        button.isEnabled = true

    }

    // método para pegar o conteúdo para inserir no banco de dados
    private fun getBook() : Book{

        var book = Book()

        if (this.mybook != null){
            book._id = mybook!!._id
            book.image = mybook!!.image
        }

        book.name = getStringForView(R.id.nomeLivro)
        book.size = Integer.parseInt(getStringForView(R.id.totalPaginasLivro))
        book.type = view?.findViewById<Spinner>(R.id.categorias)?.selectedItemPosition!!
        book.year = getStringForView(R.id.anoLivro)
        book.editora = getStringForView(R.id.editoraLivro)

        if (photoFile == null && book.image == ""){
            book.image = ""
        }else if (photoFile != null) {
            book.image = photoFile!!.absolutePath
        }

        return book
    }

    private fun getStringForView(ref: Int): String{
        return view?.findViewById<TextInputLayout>(ref)?.editText?.text.toString()
    }

    /**
     * funcã utilizada para poder inserir as validações no campo e permitir apenas habilitar a opção
     * apenas depois que todos os inputs estiverem OK
     */
    private fun validateForm(view: View){
        var inputName = view?.findViewById<TextInputLayout>(R.id.nomeLivro)!!
        var inputEditora = view?.findViewById<TextInputLayout>(R.id.editoraLivro)!!
        var inputAno = view?.findViewById<TextInputLayout>(R.id.anoLivro)!!
        var inputTotal = view?.findViewById<TextInputLayout>(R.id.totalPaginasLivro)

        var inputNameObservale: Observable<Boolean> = RxValidator(inputName).apply {
            add(Rules.NotEmpty(this@CadastroLivroFragment.context!!))
        }.asObservable()

        var inputEditoraObservable : Observable<Boolean> = RxValidator(inputEditora).apply {
            add(Rules.NotEmpty(this@CadastroLivroFragment.context!!))
            add(Rules.MinLength(4, this@CadastroLivroFragment.context!!))
        }.asObservable()

        var inputAnoObservable : Observable<Boolean> = RxValidator(inputAno).apply {
            add(Rules.NotEmpty(this@CadastroLivroFragment.context!!))
            add(Rules.MinLength(4, this@CadastroLivroFragment.context!!))
        }.asObservable()

        var inputTotalObservable: Observable<Boolean> = RxValidator(inputTotal).apply {
            add(Rules.NotEmpty(this@CadastroLivroFragment.context!!))
            add(Rules.MinLength(2, this@CadastroLivroFragment.context!!))
        }.asObservable()

        RxCombineValidator(inputNameObservale, inputEditoraObservable, inputAnoObservable,
                inputTotalObservable).asObservable().
                distinctUntilChanged().subscribe { valid ->
            view.findViewById<Button>(R.id.btnCadastroLivro).isEnabled = valid
        }

    }

    // mudar o fragment
    private fun changeFragment(){
        changeFor.let {
            val transaction = fragmentManager?.beginTransaction()
            //transaction?.hide(this) não pode utilizar hide, pois depois precisamos dar um show novamente
            transaction?.replace(R.id.mainFragm, it)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

    }

    private fun cadastro(view: View){
        DAOUtil.insertBook(getBook()) // usar um transformador para chamar o change apenas finalizado o insert book
        changeFragment()
    }


    // método utilizado para tirar foto para o usuário e salvar dentro de um diretório do próprio app
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

    // após a imagem ser capturada definir ela no meu ImaveView
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            ImageConversor().setPic(view?.findViewById<ImageView>(R.id.imageBackground)!!,
                    photoFile!!.absolutePath )
        }
    }

}
