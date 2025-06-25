package com.example.bridgey2.Models

import com.google.gson.annotations.SerializedName

data class SearchEvent(

	override val title: String? = null,
	override val logo: String? = null,

	@field:SerializedName("date")
	override val date: String? = null,

	@field:SerializedName("image_url")
	override val imageUrl: String? = null,

	@field:SerializedName("name")
	override val name: String? = null,

	@field:SerializedName("location")
	override val location: String? = null,

	@field:SerializedName("id")
	override val id: String? = null

) : SearchItem {

	override val type: String = "Event"

}
