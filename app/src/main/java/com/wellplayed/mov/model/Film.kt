package com.wellplayed.mov.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film (
    var desc:String ? = "",
    var director:String ? = "",
    var genre:String ? = "",
    var judul:String ? = "",
    var poster:String ? = "",
    var postertiket:String ? = "",
    var postercomingsoon:String ? = "",
    var rating:String ? = ""

) : Parcelable