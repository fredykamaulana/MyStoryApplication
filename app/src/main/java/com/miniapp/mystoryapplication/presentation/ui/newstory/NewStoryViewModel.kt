package com.miniapp.mystoryapplication.presentation.ui.newstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.domain.mapper.mapPostStoryRequestUiToDomain
import com.miniapp.mystoryapplication.domain.responsedomain.BaseResponseDomainModel
import com.miniapp.mystoryapplication.domain.usecase.poststory.PostStoryUseCase
import com.miniapp.mystoryapplication.presentation.requestui.PostStoryRequestUiModel
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

class NewStoryViewModel(private val postStoryuseCase: PostStoryUseCase) : ViewModel() {
    private val _postStoryResult = MutableLiveData<ResultState<BaseResponseDomainModel>>()
    val postStoryResult: LiveData<ResultState<BaseResponseDomainModel>> get() = _postStoryResult

    fun postStory(requestUiModel: PostStoryRequestUiModel) {
        _postStoryResult.value = ResultState.Loading()
        viewModelScope.launch {
            _postStoryResult.value =
                postStoryuseCase.postStory(mapPostStoryRequestUiToDomain.map(requestUiModel)).last()
        }
    }
}