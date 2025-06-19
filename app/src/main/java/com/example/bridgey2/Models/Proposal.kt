package com.example.bridgey2.Models

import java.io.Serializable

data class Proposal(
    val id: String,
    val tenantName: String,
    val logoUrl: String,
    val submissionDate: String,
    val currentStatus: String,
    val statusList: List<ProposalStatus>
) : Serializable
