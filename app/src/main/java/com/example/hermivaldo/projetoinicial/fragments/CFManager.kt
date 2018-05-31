package com.example.hermivaldo.projetoinicial.fragments

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.hermivaldo.projetoinicial.R
import com.example.hermivaldo.projetoinicial.services.BookUtil
import com.example.hermivaldo.projetoinicial.util.DbWorkThread

open class CFManager : AppCompatActivity() {

    var mThread = DbWorkThread("dbWorkerThread")
    lateinit var bookUtil: BookUtil

    var listFragment: ListFragment = ListFragment()
    var cadastroLivroFragment: CadastroLivroFragment = CadastroLivroFragment()
    var mapFragment: MapFragment = MapFragment()
    var settingsFragment: SettingsFragment = SettingsFragment()

    protected fun inicializaFragments(){
        this.listFragment.loadThread(bookUtil)
        this.cadastroLivroFragment.loadThread(bookUtil)

    }

    fun changeFragment(fragment: Fragment){
        var transition = supportFragmentManager.beginTransaction()
        transition.replace(R.id.mainFragm, fragment)
        transition.commit()
    }

    protected val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                changeFragment(this.listFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                changeFragment(this.cadastroLivroFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                changeFragment(this.mapFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_configuracao -> {
                changeFragment(this.settingsFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}