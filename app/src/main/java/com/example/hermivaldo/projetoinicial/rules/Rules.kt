package com.example.hermivaldo.projetoinicial.rules

import ru.whalemare.rxvalidator.ValidateRule

class Rules {

    class NotNUll : ValidateRule {

        override fun errorMessage(): String {
            return "Campo não pode ser vazio"
        }

        override fun validate(data: String?): Boolean {
            return data?.length != 0
        }

    }

    class MinLength(val size: Int) : ValidateRule {

        override fun errorMessage(): String {
            return "Campo inválido"
        }

        override fun validate(data: String?): Boolean {
            return data!!.length >= size
        }
    }
}