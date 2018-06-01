package com.example.hermivaldo.projetoinicial

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.hermivaldo.projetoinicial.rules.Rules
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import ru.whalemare.rxvalidator.RxCombineValidator
import ru.whalemare.rxvalidator.RxValidator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        validation()
    }

    fun validation (){
        var user: Observable<Boolean> = RxValidator(loginInput).apply {
            add(Rules.NotNUll())
            add(Rules.MinLength(4))
        }.asObservable()
        var pass: Observable<Boolean> = RxValidator(passInput).apply {
            add(Rules.NotNUll())
        }.asObservable()

        RxCombineValidator(user, pass).asObservable().distinctUntilChanged().subscribe { valid ->
            btnLogin.isEnabled = valid
        }
    }

    fun realizarLogin(view: View){
        var newPage = Intent(this, Painel::class.java)
        startActivity(newPage)
        finish()
    }
}
