package com.example.bridgey2.Models

import java.io.Serializable

data class ProposalStatus(
    val title: String,
    val description: String,
    val fileName: String? = null,
    val dateTime: String,
    val isLatest: Boolean = false
) : Serializable
