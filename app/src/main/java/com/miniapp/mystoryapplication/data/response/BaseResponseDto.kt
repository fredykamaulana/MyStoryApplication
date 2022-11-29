package com.miniapp.mystoryapplication.data.response

import com.squareup.moshi.Json

data class BaseResponseDto(

	@field:Json(name="error")
	val error: Boolean? = null,

	@field:Json(name="message")
	val message: String? = null
)
