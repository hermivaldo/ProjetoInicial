package com.example.hermivaldo.projetoinicial

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.hermivaldo.projetoinicial.fragments.CadastroLivroFragment
import com.example.hermivaldo.projetoinicial.fragments.ListFragment
import com.example.hermivaldo.projetoinicial.fragments.MapFragment
import com.example.hermivaldo.projetoinicial.fragments.SettingsFragment
import com.example.hermivaldo.projetoinicial.services.BookUtil
import com.example.hermivaldo.projetoinicial.util.DbWorkThread
import kotlinx.android.synthetic.main.activity_painel.*
import kotlinx.android.synthetic.main.fragment_list.*

class Painel : AppCompatActivity() {

    var mThread = DbWorkThread("dbWorkerThread")
    lateinit var bookUtil: BookUtil

    var listFragment: ListFragment = ListFragment()
    var cadastroLivroFragment: CadastroLivroFragment = CadastroLivroFragment()
    var mapFragment: MapFragment = MapFragment()
    var settingsFragment: SettingsFragment = SettingsFragment()

    private val itensMenu: IntArray = intArrayOf(R.id.navigation_home,
            R.id.navigation_dashboard, R.id.navigation_notifications,
            R.id.navigation_configuracao)

    private fun inicializaFragments(){
        this.listFragment.loadThread(bookUtil)
        this.cadastroLivroFragment.loadThread(bookUtil)

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
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

    fun changeFragment(fragment: Fragment){
        var transition = supportFragmentManager.beginTransaction()
        transition.replace(R.id.mainFragm, fragment)
        transition.commit()
    }

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
        inicializaFragments()
        changeFragment(this.listFragment)
        actionBar?.hide()
    }
}
