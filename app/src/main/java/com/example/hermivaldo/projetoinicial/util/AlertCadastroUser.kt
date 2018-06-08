package com.example.hermivaldo.projetoinicial.util

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.hermivaldo.projetoinicial.R
import com.example.hermivaldo.projetoinicial.entity.User
import com.example.hermivaldo.projetoinicial.rules.Rules
import com.example.hermivaldo.projetoinicial.services.DAOUtil
import io.reactivex.Observable
import ru.whalemare.rxvalidator.RxCombineValidator
import ru.whalemare.rxvalidator.RxValidator


class AlertCadastroUser : DialogFragment() {


    var mThread = DbWorkThread("dbWorkerThread")
    lateinit var DAOUtil: DAOUtil

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity!!)

        val inflater = activity!!.layoutInflater
        mThread.start()
        DAOUtil = DAOUtil(activity!!.applicationContext, mThread)

        val view = inflater.inflate(R.layout.modal_cad_user, null)

        inserirValidacao(view)
        builder.setView(view)
                .setPositiveButton(R.string.sign_in, { dialog, which ->

                    var user = User()
                    user.name = view?.findViewById<TextInputLayout>(R.id.username)?.editText?.text.toString()
                    user.password = view?.findViewById<TextInputLayout>(R.id.password)?.editText?.text.toString()
                    DAOUtil.insertUser(user)
                })
                .setNegativeButton(R.string.cancel, { dialog, which ->

                })
        return builder.create()
    }

    private fun inserirValidacao(view: View){
        var inputUser = view?.findViewById<TextInputLayout>(R.id.username)!!
        var inputPassword = view?.findViewById<TextInputLayout>(R.id.password)!!
        var inputRePassword = view?.findViewById<TextInputLayout>(R.id.repassword)!!

        var inputNameObservale: Observable<Boolean> = RxValidator(inputUser).apply {
            add(Rules.NotEmpty(view.context))
            add(Rules.MinLength(4, view.context))
        }.asObservable()

        var inputPasswordObservale: Observable<Boolean> = RxValidator(inputPassword).apply {
            add(Rules.NotEmpty(view.context))
        }.asObservable()

        var inputRePasswordObservale: Observable<Boolean> = RxValidator(inputRePassword).apply {
            add(Rules.NotEmpty(view.context))
            add(Rules.EqualsTo(inputPassword.editText!!, view.context))
        }.asObservable()

        RxCombineValidator(inputNameObservale, inputRePasswordObservale, inputPasswordObservale).asObservable().
                distinctUntilChanged().subscribe { valid ->
            val dialog = dialog as AlertDialog
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = valid
        }

    }

    override fun onResume() {
        super.onResume()

        // disable positive button by default
        val dialog = dialog as AlertDialog
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
    }

    override fun onDestroy() {
        super.onDestroy()
        mThread.quit()
    }
}