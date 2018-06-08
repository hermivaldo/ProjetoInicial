package com.example.hermivaldo.projetoinicial

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.hermivaldo.projetoinicial.entity.User
import com.example.hermivaldo.projetoinicial.rules.Rules
import com.example.hermivaldo.projetoinicial.services.DAOUtil
import com.example.hermivaldo.projetoinicial.util.AlertCadastroUser
import com.example.hermivaldo.projetoinicial.util.DbWorkThread
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import ru.whalemare.rxvalidator.RxCombineValidator
import ru.whalemare.rxvalidator.RxValidator

class MainActivity : AppCompatActivity() {

    var mThread = DbWorkThread("outro")
    lateinit var DAOUtil: DAOUtil

    override fun onCreate(savedInstanceState: Bundle?) {

        val sharedPref = this.getSharedPreferences( "_preferences" , Context.MODE_PRIVATE) ?: return

        val precisaLogar = sharedPref.getBoolean(getString(R.string.user_system), false)
        if (precisaLogar){
            mudarTela()
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mThread.start()
        DAOUtil = DAOUtil(applicationContext, mThread)
        validation()
    }

    fun validation (){
        var user: Observable<Boolean> = RxValidator(loginInput).apply {
            add(Rules.NotEmpty(this@MainActivity))
            add(Rules.MinLength(4, this@MainActivity))
        }.asObservable()
        var pass: Observable<Boolean> = RxValidator(passInput).apply {
            add(Rules.NotEmpty(this@MainActivity))
        }.asObservable()

        RxCombineValidator(user, pass).asObservable().distinctUntilChanged().subscribe { valid ->
            btnLogin.isEnabled = valid
        }
    }

    private fun mudarTela(){
        var newPage = Intent(this, Painel::class.java)
        startActivity(newPage)
        finish()
    }

    fun cadastrarUsuario(view: View){
        val alertCadastroUser = AlertCadastroUser()
        alertCadastroUser.show(supportFragmentManager, "cadastro")
    }

    fun realizarLogin(view: View){
        var user = User()
        user.name = loginInput.editText?.text.toString()
        user.password = passInput.editText?.text.toString()

        DAOUtil.getUser(user, {
            if (it != null){
                val sharedPref = this.getSharedPreferences( "_preferences" , Context.MODE_PRIVATE)
                with (sharedPref.edit()) {
                    putBoolean(getString(R.string.user_system), permanecer_logado.isChecked)
                    commit()
                    mudarTela()
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mThread.quit()
    }


}
