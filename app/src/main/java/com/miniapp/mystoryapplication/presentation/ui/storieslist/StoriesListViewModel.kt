package com.miniapp.mystoryapplication.presentation.ui.storieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.domain.responsedomain.StoriesResponseDomainModel
import com.miniapp.mystoryapplication.domain.usecase.getstories.GetStoriesUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

class StoriesListViewModel(private val getStoriesUseCases: GetStoriesUseCases) : ViewModel() {

    private val _getStoriesList = MutableLiveData<ResultState<StoriesResponseDomainModel>>()
    val getStoriesList: LiveData<ResultState<StoriesResponseDomainModel>> get() = _getStoriesList

    fun getStoriesList() {
        _getStoriesList.value = ResultState.Loading()
        viewModelScope.launch {
            _getStoriesList.value = getStoriesUseCases.getStories().last()
        }
    }

    fun getStoriesList(pageSize: Int): Flow<PagingData<StoriesResponseDomainModel.ListStoryItem>> {
        return getStoriesUseCases.getStoriesWithPaging(pageSize = pageSize)
    }
}