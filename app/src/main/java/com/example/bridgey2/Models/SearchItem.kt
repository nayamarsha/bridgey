package com.example.bridgey2.Models

interface SearchItem {
    val name: String?
    val date: String?
    val imageUrl: String?
    val location: String?
    val id: String?
    val title: String?
    val logo: String?
    val type: String // "Event", "Sponsor", dll
}
