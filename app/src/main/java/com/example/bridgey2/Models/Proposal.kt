package com.example.bridgey2.Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Proposal(
    val id: String,
    val tenantName: String,
    val logoUrl: String,
    val submissionDate: String,
    val currentStatus: String,
    val statusList: List<ProposalStatus>
) : Parcelable
