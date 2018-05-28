package com.example.hermivaldo.projetoinicial.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "book")
data class Book (@PrimaryKey(autoGenerate = true) var _id: Int, var name: String, var type: Int, var size: Int, var year: String, var image: String, var editora: String){
    constructor():this(0,"",0,0, "", "", "")
}