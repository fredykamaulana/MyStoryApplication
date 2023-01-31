package com.miniapp.mystoryapplication.presentation.ui.storieslist.utils

import androidx.recyclerview.widget.ListUpdateCallback
import com.miniapp.mystoryapplication.data.response.StoriesResponseDto
import com.miniapp.mystoryapplication.domain.responsedomain.StoriesResponseDomainModel

object DummyDataUtils {

    fun populateStoriesDummyData(): StoriesResponseDomainModel {
        val storiesList = mutableListOf<StoriesResponseDomainModel.ListStoryItem>()
        for (i in 0..10) {
            storiesList.add(
                StoriesResponseDomainModel.ListStoryItem(
                    id = "STORY$i",
                    name = "User $i",
                    description = "Description $i",
                    photoUrl = "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                    createdAt = "2023-03-23T23:23:23Z",
                    lat = 1.2345678f,
                    lon = -1.2345678f
                )
            )
        }

        return StoriesResponseDomainModel(
            error = false,
            message = "success get data",
            listStory = storiesList
        )
    }

    fun populateEmptyStoriesDummyData(): StoriesResponseDomainModel = StoriesResponseDomainModel(
        error = false,
        message = "success get data",
        listStory = emptyList()
    )

    fun populateResponseStoriesListItem(): List<StoriesResponseDto.ListStoryItem> {
        val storiesList = mutableListOf<StoriesResponseDto.ListStoryItem>()
        for (i in 0..10) {
            storiesList.add(
                StoriesResponseDto.ListStoryItem(
                    id = "STORY$i",
                    name = "User $i",
                    description = "Description $i",
                    photoUrl = "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                    createdAt = "2023-03-23T23:23:23Z",
                    lat = 1.2345678f,
                    lon = -1.2345678f
                )
            )
        }

        return storiesList
    }

    val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}