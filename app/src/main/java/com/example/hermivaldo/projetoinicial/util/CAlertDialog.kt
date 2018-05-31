package com.example.hermivaldo.projetoinicial.util


import android.app.Dialog
import android.os.Bundle
import android.content.DialogInterface
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog


class CAlertDialog() : DialogFragment() {
    var delete: (Boolean) -> Unit? = {}

    fun inicializar(delete: (Boolean) -> Unit){
        this.delete = delete
    }

    override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
        // Use the Builder class for convenient dialog construction
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Alerta de mensagem")
        builder.setMessage("Deseja excluir o livro")
//        builder.setPositiveButton("DELETAR", { dialog, id ->
//            this.delete(true)
//        })
//        builder.setNegativeButton("CANCELAR", DialogInterface.OnClickListener { dialog, id ->
//                    this.delete(false)
//                })
        // Create the AlertDialog object and return it
        return builder.create()
    }
}