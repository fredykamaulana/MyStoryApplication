package com.miniapp.mystoryapplication.core.di

import android.content.Context
import android.content.SharedPreferences
import com.miniapp.mystoryapplication.BuildConfig
import com.miniapp.mystoryapplication.data.repository.GetStoriesRepositoryImpl
import com.miniapp.mystoryapplication.data.repository.LoginRepositoryImpl
import com.miniapp.mystoryapplication.data.repository.PostStoryRepositoryImpl
import com.miniapp.mystoryapplication.data.repository.PreferencesRepositoryImpl
import com.miniapp.mystoryapplication.data.repository.RegisterRepositoryImpl
import com.miniapp.mystoryapplication.data.service.GetStoriesApiService
import com.miniapp.mystoryapplication.data.service.LoginApiService
import com.miniapp.mystoryapplication.data.service.PostStoryApiService
import com.miniapp.mystoryapplication.data.service.RegisterApiService
import com.miniapp.mystoryapplication.data.source.GetStoriesRemoteDataSource
import com.miniapp.mystoryapplication.data.source.LoginRemoteDataSource
import com.miniapp.mystoryapplication.data.source.PostStoryRemoteDataSource
import com.miniapp.mystoryapplication.data.source.PreferencesDataSource
import com.miniapp.mystoryapplication.data.source.RegisterRemoteDataSource
import com.miniapp.mystoryapplication.data.utils.AuthenticationInterceptor
import com.miniapp.mystoryapplication.domain.repository.GetStoriesRepository
import com.miniapp.mystoryapplication.domain.repository.LoginRepository
import com.miniapp.mystoryapplication.domain.repository.PostStoryRepository
import com.miniapp.mystoryapplication.domain.repository.PreferencesRepository
import com.miniapp.mystoryapplication.domain.repository.RegisterRepository
import com.miniapp.mystoryapplication.domain.usecase.getstories.GetStoriesInteractor
import com.miniapp.mystoryapplication.domain.usecase.getstories.GetStoriesUseCases
import com.miniapp.mystoryapplication.domain.usecase.login.LoginInteractor
import com.miniapp.mystoryapplication.domain.usecase.login.LoginUseCases
import com.miniapp.mystoryapplication.domain.usecase.logout.LogoutInteractor
import com.miniapp.mystoryapplication.domain.usecase.logout.LogoutUseCase
import com.miniapp.mystoryapplication.domain.usecase.poststory.PostStoryInteractor
import com.miniapp.mystoryapplication.domain.usecase.poststory.PostStoryUseCase
import com.miniapp.mystoryapplication.domain.usecase.preferences.PreferencesInteractor
import com.miniapp.mystoryapplication.domain.usecase.preferences.PreferencesUseCases
import com.miniapp.mystoryapplication.domain.usecase.register.RegisterInteractor
import com.miniapp.mystoryapplication.domain.usecase.register.RegisterUseCases
import com.miniapp.mystoryapplication.presentation.ui.login.LoginViewModel
import com.miniapp.mystoryapplication.presentation.ui.logout.LogoutViewModel
import com.miniapp.mystoryapplication.presentation.ui.newstory.NewStoryViewModel
import com.miniapp.mystoryapplication.presentation.ui.register.RegisterViewModel
import com.miniapp.mystoryapplication.presentation.ui.splash.SplashViewModel
import com.miniapp.mystoryapplication.presentation.ui.storieslist.StoriesListViewModel
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun injectKoinModules() = loadModules

private val loadModules by lazy {
    loadKoinModules(modules)
}

const val SHARED_PREFERENCES_NAME = "myStory_preferences"

val preferencesModule: Module = module {
    single { provideSharedPreferences(androidContext()) }
}

private fun provideSharedPreferences(context: Context): SharedPreferences =
    context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

@Suppress("SpellCheckingInspection")
val networkModule: Module = module {

    single {
        if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .addInterceptor(AuthenticationInterceptor(preferences = get()))
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build()
        } else {
            OkHttpClient.Builder()
                .addInterceptor(AuthenticationInterceptor(preferences = get()))
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build()
        }
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create().withNullSerialization())
            .build()
    }
}

val apiModule: Module = module {
    single { get<Retrofit>().create(GetStoriesApiService::class.java) }
    single { get<Retrofit>().create(LoginApiService::class.java) }
    single { get<Retrofit>().create(PostStoryApiService::class.java) }
    single { get<Retrofit>().create(RegisterApiService::class.java) }
}

val dataSource: Module = module {
    single { LoginRemoteDataSource(apiService = get()) }
    single { RegisterRemoteDataSource(apiService = get()) }
    single { GetStoriesRemoteDataSource(apiService = get()) }
    single { PreferencesDataSource(preferences = get()) }
    single { PostStoryRemoteDataSource(apiService = get()) }
}

val repository: Module = module {
    single<LoginRepository> { LoginRepositoryImpl(remoteDataSource = get()) }
    single<RegisterRepository> { RegisterRepositoryImpl(remoteDataSource = get()) }
    single<GetStoriesRepository> { GetStoriesRepositoryImpl(remoteDataSource = get()) }
    single<PreferencesRepository> { PreferencesRepositoryImpl(source = get()) }
    single<PostStoryRepository> { PostStoryRepositoryImpl(remoteDataSource = get()) }
}

val useCases: Module = module {
    factory<LoginUseCases> { LoginInteractor(repository = get()) }
    factory<RegisterUseCases> { RegisterInteractor(repository = get()) }
    factory<GetStoriesUseCases> { GetStoriesInteractor(repository = get()) }
    factory<PreferencesUseCases> { PreferencesInteractor(repository = get()) }
    factory<PostStoryUseCase> { PostStoryInteractor(repository = get()) }
    factory<LogoutUseCase> { LogoutInteractor(repository = get()) }
}

val viewModel: Module = module {
    viewModel { LoginViewModel(loginUseCases = get(), preferencesUseCases = get()) }
    viewModel { RegisterViewModel(registerUseCases = get()) }
    viewModel { StoriesListViewModel(getStoriesUseCases = get()) }
    viewModel { SplashViewModel(preferencesUseCases = get()) }
    viewModel { NewStoryViewModel(postStoryuseCase = get()) }
    viewModel { LogoutViewModel(useCase = get()) }
}

val modules = listOf(
    preferencesModule,
    networkModule,
    apiModule,
    dataSource,
    repository,
    useCases,
    viewModel
)