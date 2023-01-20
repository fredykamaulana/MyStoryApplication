package com.miniapp.mystoryapplication.data.response

import com.squareup.moshi.Json

data class StoriesResponseDto(

	@field:Json(name = "error")
	val error: Boolean? = null,

	@field:Json(name = "message")
	val message: String? = null,

	@field:Json(name = "listStory")
	val listStory: List<ListStoryItem> = emptyList()
) {
	data class ListStoryItem(

		@field:Json(name = "id")
		val id: String? = null,

		@field:Json(name = "name")
		val name: String? = null,

		@field:Json(name = "description")
		val description: String? = null,

		@field:Json(name = "photoUrl")
		val photoUrl: String? = null,

		@field:Json(name = "createdAt")
		val createdAt: String? = null,

		@field:Json(name = "lat")
		val lat: Float? = null,

		@field:Json(name = "lon")
		val lon: Float? = null
	)
}