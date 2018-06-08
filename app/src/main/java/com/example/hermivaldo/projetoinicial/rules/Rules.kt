package com.example.hermivaldo.projetoinicial.rules

import android.content.Context
import android.widget.EditText
import com.example.hermivaldo.projetoinicial.R
import ru.whalemare.rxvalidator.ValidateRule

class Rules {

    class NotEmpty(var context: Context) : ValidateRule {

        override fun errorMessage(): String {
            return  context.resources.getString(R.string.empty_rule)
        }

        override fun validate(data: String?): Boolean {
            return data?.length != 0
        }

    }

    class MinLength(val size: Int, var context: Context) : ValidateRule {

        override fun errorMessage(): String {
            return String.format(context.resources.getString(R.string.minlength_rule), size)
        }

        override fun validate(data: String?): Boolean {
            return data!!.length >= size
        }
    }

    class EqualsTo(val word: EditText, var context: Context) : ValidateRule {

        override fun errorMessage(): String {
            return context.resources.getString(R.string.pass_rule)
        }

        override fun validate(data: String?): Boolean {
            return data!!.equals(word.text.toString())
        }
    }
}