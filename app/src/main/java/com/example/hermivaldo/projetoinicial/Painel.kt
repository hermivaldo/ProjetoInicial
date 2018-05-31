package com.example.hermivaldo.projetoinicial

import android.os.Bundle
import com.example.hermivaldo.projetoinicial.fragments.*
import com.example.hermivaldo.projetoinicial.services.BookUtil
import kotlinx.android.synthetic.main.activity_painel.*


class Painel : CFManager() {

    override fun onDestroy() {
        super.onDestroy()
        mThread.quit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_painel)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        mThread.start()
        bookUtil = BookUtil(applicationContext, mThread)
        super.inicializaFragments()
        changeFragment(this.listFragment)

    }
}
