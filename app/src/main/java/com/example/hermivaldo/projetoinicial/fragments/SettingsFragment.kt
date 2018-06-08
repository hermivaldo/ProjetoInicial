package com.example.hermivaldo.projetoinicial.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hermivaldo.projetoinicial.MainActivity

import com.example.hermivaldo.projetoinicial.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.fragment_settings)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        if (view != null) {
            view.setPadding(10,30,10,10)
        }

        val button = preferenceManager.findPreference("resetLogin") as Preference
        if (button != null) {
            button.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                val sharedPref = container?.context?.getSharedPreferences( "_preferences" , Context.MODE_PRIVATE)
                with (sharedPref!!.edit()) {
                    putBoolean(getString(com.example.hermivaldo.projetoinicial.R.string.user_system), false)

                    commit()
                }
                var intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                onDestroy()
                true
            }
        }
        return view
    }

}
