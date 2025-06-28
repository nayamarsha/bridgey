package com.example.bridgey2.Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sponsor(
    val description: String?=null,
    val logo: String?=null,
    val name: String?=null,
    var id: String?=null,
    var email: String?=null,
): Parcelable
