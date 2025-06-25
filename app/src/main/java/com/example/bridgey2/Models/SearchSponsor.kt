package com.example.bridgey2.Models

import com.google.gson.annotations.SerializedName

data class SearchSponsor(

	override val date: String? = null,
	override val imageUrl: String? = null,
	override val location: String? = null,

	@field:SerializedName("name")
	override val name: String? = null,

	@field:SerializedName("logo")
	override val logo: String? = null,

	@field:SerializedName("id")
	override val id: String? = null,

	@field:SerializedName("title")
	override val title: String? = null

) : SearchItem {

	override val type: String = "Sponsor"

}
