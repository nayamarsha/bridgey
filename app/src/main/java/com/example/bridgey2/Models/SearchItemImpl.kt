package com.example.bridgey2.Models

data class SearchItemImpl(
    override val name: String? = null,
    override val date: String? = null,
    override val imageUrl: String? = null,
    override val location: String? = null,
    override val id: String? = null,
    override val title: String? = null,
    override val logo: String? = null,
    override val type: String
) : SearchItem
