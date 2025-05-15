package com.example.bridgey2.Models

import com.google.gson.annotations.SerializedName

data class ResponseEvent(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
