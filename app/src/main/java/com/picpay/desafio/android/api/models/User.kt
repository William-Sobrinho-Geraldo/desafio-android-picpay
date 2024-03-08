package com.picpay.desafio.android.api.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//@Parcelize
@Entity(tableName = "tabela_de_usuarios")
data class User(
    @SerializedName("img") val img: String?,
    @SerializedName("name") val name: String?,
    @PrimaryKey @SerializedName("id") val id: Int?,
    @SerializedName("username") val username: String?,
) //: Parcelable
//{
//    override fun describeContents(): Int {
////        TODO("Not yet implemented")
//        return 0
//    }
//
//    override fun writeToParcel(p0: Parcel, p1: Int) {
//    }
//}