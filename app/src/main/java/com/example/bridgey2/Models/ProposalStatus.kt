package com.example.bridgey2.Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProposalStatus(
    val title: String,
    val description: String,
    val fileName: String? = null,
    val dateTime: String,
    val isLatest: Boolean = false
) : Parcelable
