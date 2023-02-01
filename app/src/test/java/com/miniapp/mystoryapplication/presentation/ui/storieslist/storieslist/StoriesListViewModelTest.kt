package com.miniapp.mystoryapplication.presentation.ui.storieslist.storieslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.map
import com.miniapp.mystoryapplication.data.response.StoriesResponseDto
import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.domain.responsedomain.StoriesResponseDomainModel
import com.miniapp.mystoryapplication.domain.usecase.getstories.GetStoriesUseCases
import com.miniapp.mystoryapplication.presentation.responseui.StoriesResponseUiModel
import com.miniapp.mystoryapplication.presentation.ui.storieslist.StoriesListViewModel
import com.miniapp.mystoryapplication.presentation.ui.storieslist.adapter.StoryListPagingAdapter
import com.miniapp.mystoryapplication.presentation.ui.storieslist.utils.DummyDataUtils.noopListUpdateCallback
import com.miniapp.mystoryapplication.presentation.ui.storieslist.utils.DummyDataUtils.populateEmptyStoriesDummyData
import com.miniapp.mystoryapplication.presentation.ui.storieslist.utils.DummyDataUtils.populateResponseStoriesListItem
import com.miniapp.mystoryapplication.presentation.ui.storieslist.utils.DummyDataUtils.populateStoriesDummyData
import com.miniapp.mystoryapplication.presentation.ui.storieslist.utils.GetStoriesPagingSourceTest
import com.miniapp.mystoryapplication.presentation.ui.storieslist.utils.MainCoroutinesRule
import com.miniapp.mystoryapplication.presentation.ui.storieslist.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoriesListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutinesRule = MainCoroutinesRule()

    @Mock
    lateinit var getStoriesUseCase: GetStoriesUseCases
    lateinit var sut: StoriesListViewModel

    @Before
    fun setup() {
        sut = StoriesListViewModel(getStoriesUseCases = getStoriesUseCase)
    }

    @Test
    fun `when get stories should not null and data is not empty`(): Unit = runTest {

        //given
        val expectedResult = MutableLiveData<ResultState<StoriesResponseDomainModel>>()
        expectedResult.value = ResultState.Success(populateStoriesDummyData())

        //when
        `when`(getStoriesUseCase.getStories()).thenReturn(
            flow { emit(expectedResult.value as ResultState<StoriesResponseDomainModel>) }
        )

        sut.getStoriesList()
        verify(getStoriesUseCase).getStories()

        //then
        val actualResult = sut.getStoriesList.getOrAwaitValue()

        Assert.assertNotNull(actualResult)
        Assert.assertEquals(
            populateStoriesDummyData().listStory.size,
            actualResult.data?.listStory?.size
        )
        Assert.assertEquals(
            populateStoriesDummyData().listStory[0],
            actualResult.data?.listStory?.get(0)
        )
    }

    @Test
    fun `when get stories should not null and data is empty`(): Unit = runTest {

        //given
        val expectedResult = MutableLiveData<ResultState<StoriesResponseDomainModel>>()
        expectedResult.value = ResultState.Success(populateEmptyStoriesDummyData())

        //when
        `when`(getStoriesUseCase.getStories()).thenReturn(
            flow { emit(expectedResult.value as ResultState<StoriesResponseDomainModel>) }
        )

        sut.getStoriesList()
        verify(getStoriesUseCase).getStories()

        //then
        val actualResult = sut.getStoriesList.getOrAwaitValue()

        Assert.assertNotNull(actualResult)
        Assert.assertTrue(actualResult is ResultState.Success)
        Assert.assertEquals(
            0,
            actualResult.data?.listStory?.size
        )
    }

    @Test
    fun `when get stories paging data should not null and data is not empty`(): Unit = runTest {

        //given
        val pageSize = 5
        val data: PagingData<StoriesResponseDto.ListStoryItem> =
            GetStoriesPagingSourceTest.snapshot(populateResponseStoriesListItem())
        val expectedResult = MutableLiveData<PagingData<StoriesResponseDto.ListStoryItem>>()
        expectedResult.value = data

        //when
        `when`(getStoriesUseCase.getStoriesWithPaging(pageSize = pageSize)).thenReturn(
            flow {
                emit(
                    (expectedResult.value as PagingData<StoriesResponseDto.ListStoryItem>).map { stories ->
                        StoriesResponseDomainModel.ListStoryItem(
                            id = stories.id.orEmpty(),
                            name = stories.name.orEmpty(),
                            description = stories.description.orEmpty(),
                            photoUrl = stories.photoUrl.orEmpty(),
                            createdAt = stories.createdAt.orEmpty(),
                            lat = stories.lat ?: 0.0f,
                            lon = stories.lon ?: 0.0f
                        )
                    })
            })

        sut.getStoriesList(pageSize = pageSize)
        verify(getStoriesUseCase).getStoriesWithPaging(pageSize = pageSize)

        //then
        val actualResult = sut.getStoriesList(pageSize = pageSize).asLiveData()
        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryListPagingAdapter.diffCallback,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(
            actualResult.getOrAwaitValue().map { story ->
                StoriesResponseUiModel(
                    id = story.id,
                    name = story.name,
                    description = story.description,
                    photoUrl = story.photoUrl,
                    createdAt = story.createdAt,
                    lat = story.lat,
                    lon = story.lon
                )
            })

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(populateResponseStoriesListItem().size, differ.snapshot().size)
        Assert.assertEquals(
            populateResponseStoriesListItem()[0].id,
            differ.snapshot()[0]?.id
        )
    }

    @Test
    fun `when get stories paging data should not null and data is empty`(): Unit = runTest {

        //given
        val pageSize = 5
        val data: PagingData<StoriesResponseDto.ListStoryItem> =
            GetStoriesPagingSourceTest.snapshot(emptyList())
        val expectedResult = MutableLiveData<PagingData<StoriesResponseDto.ListStoryItem>>()
        expectedResult.value = data

        //when
        `when`(getStoriesUseCase.getStoriesWithPaging(pageSize = pageSize)).thenReturn(
            flow {
                emit(
                    (expectedResult.value as PagingData<StoriesResponseDto.ListStoryItem>).map { stories ->
                        StoriesResponseDomainModel.ListStoryItem(
                            id = stories.id.orEmpty(),
                            name = stories.name.orEmpty(),
                            description = stories.description.orEmpty(),
                            photoUrl = stories.photoUrl.orEmpty(),
                            createdAt = stories.createdAt.orEmpty(),
                            lat = stories.lat ?: 0.0f,
                            lon = stories.lon ?: 0.0f
                        )
                    })
            })

        sut.getStoriesList(pageSize = pageSize)
        verify(getStoriesUseCase).getStoriesWithPaging(pageSize = pageSize)

        //then
        val actualResult = sut.getStoriesList(pageSize = pageSize).asLiveData()
        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryListPagingAdapter.diffCallback,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(
            actualResult.getOrAwaitValue().map { story ->
                StoriesResponseUiModel(
                    id = story.id,
                    name = story.name,
                    description = story.description,
                    photoUrl = story.photoUrl,
                    createdAt = story.createdAt,
                    lat = story.lat,
                    lon = story.lon
                )
            })

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(
            0,
            differ.snapshot().size
        )
    }
}