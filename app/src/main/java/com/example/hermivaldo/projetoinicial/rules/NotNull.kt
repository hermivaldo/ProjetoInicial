package com.example.hermivaldo.projetoinicial.rules

import ru.whalemare.rxvalidator.ValidateRule

class NotNull : ValidateRule {

    override fun errorMessage(): String {
        return "Campo não pode ser vazio"
    }

    override fun validate(data: String?): Boolean {
        return data?.length != 0
    }
}