package com.miniapp.mystoryapplication.domain.responsedomain

data class StoriesResponseDomainModel(
    val error: Boolean,
    val message: String,
    val listStory: List<ListStoryItem?>
) {
    data class ListStoryItem(
        val id: String,
        val name: String,
        val description: String,
        val photoUrl: String,
        val createdAt: String,
        val lat: Float,
        val lon: Float
    )
}