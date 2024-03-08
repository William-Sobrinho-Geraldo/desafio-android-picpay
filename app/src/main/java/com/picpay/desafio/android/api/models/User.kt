package com.picpay.desafio.android.api.models

import com.google.gson.annotations.SerializedName

//@Parcelize
data class User(
    @SerializedName("img") val img: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("id") val id: Int?,
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