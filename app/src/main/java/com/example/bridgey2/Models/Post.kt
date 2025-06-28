package com.example.bridgey2.Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val eventTitle: String?=null,
    val eventDesc: String?=null,
    val eventDate: String?=null,
    val eventLocation: String?=null,
    val eventCP: String?=null,
    val eventImg: String?="",
    var id: String?=null
): Parcelable
