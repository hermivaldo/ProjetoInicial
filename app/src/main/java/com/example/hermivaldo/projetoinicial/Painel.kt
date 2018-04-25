package com.example.hermivaldo.projetoinicial

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.hermivaldo.projetoinicial.fragments.CadastroLivroFragment
import com.example.hermivaldo.projetoinicial.fragments.ListFragment
import com.example.hermivaldo.projetoinicial.fragments.MapFragment
import kotlinx.android.synthetic.main.activity_painel.*
import kotlinx.android.synthetic.main.fragment_list.*

class Painel : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                changeFragment(ListFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                //message.setText(R.string.title_dashboard)
                changeFragment(CadastroLivroFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                changeFragment(MapFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_configuracao -> {
                //message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun changeFragment(fragment: Fragment){
        var transition = supportFragmentManager.beginTransaction()
        transition.replace(R.id.mainFragm, fragment)
        transition.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_painel)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        changeFragment(ListFragment())
        actionBar?.hide()
    }
}
