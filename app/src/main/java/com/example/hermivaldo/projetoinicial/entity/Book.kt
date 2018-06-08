package com.example.hermivaldo.projetoinicial.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "book")
class Book (
        @PrimaryKey(autoGenerate = true) @SerializedName("sku")
        var _id: Int,
        var name: String,
        var type: Int,
        @SerializedName("attributes/s645/value")
        var size: Int,
        var year: String,
        var image: String,
        @SerializedName("brand")
        var editora: String,
        var description: String) : Parcelable{

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    constructor():this(0,"",0,0, "", "", "", "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(_id)
        parcel.writeString(name)
        parcel.writeInt(type)
        parcel.writeInt(size)
        parcel.writeString(year)
        parcel.writeString(image)
        parcel.writeString(editora)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }

}