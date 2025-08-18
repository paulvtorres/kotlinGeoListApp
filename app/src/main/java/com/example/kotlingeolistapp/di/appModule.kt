package com.example.kotlingeolistapp.di

import android.content.Context
import androidx.room.Room
import com.example.kotlingeolistapp.core.Constants.BASE_URL
import com.example.kotlingeolistapp.data.local.database.AppDatabase
import com.example.kotlingeolistapp.data.remote.api.CountryApi
import com.example.kotlingeolistapp.data.remote.repository.CountryRepositoryImpl
import com.example.kotlingeolistapp.data.local.FavoriteCountryDao
import com.example.kotlingeolistapp.data.local.repository.DaoCountryRepositoryImpl
import com.example.kotlingeolistapp.domain.local.repository.DaoCountryRepository
import com.example.kotlingeolistapp.domain.remote.repository.CountryRepository
import com.example.kotlingeolistapp.domain.remote.usecases.GetCountriesUseCase
import com.example.kotlingeolistapp.domain.util.ConnectivityObserver
import com.example.kotlingeolistapp.domain.util.NetworkConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideCountryApi(retrofit: Retrofit): CountryApi =
        retrofit.create(CountryApi::class.java)

    @Provides
    @Singleton
    fun provideCountryRepository(api: CountryApi): CountryRepository =
        CountryRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideDaoCountryRepository(dao: FavoriteCountryDao): DaoCountryRepository =
        DaoCountryRepositoryImpl(dao)

    @Provides
    fun provideGetCountriesUseCase(repository: CountryRepository): GetCountriesUseCase =
        GetCountriesUseCase(repository)

    @Module
    @InstallIn(SingletonComponent::class)
    object LocalModule {

        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "countries-db"
            ).build()

        @Provides
        fun provideFavoriteCountryDao(db: AppDatabase): FavoriteCountryDao = db.favoriteCountryDao()
    }

    @Provides
    @Singleton
    fun provideConnectivityObserver(
        @ApplicationContext context: Context
    ): ConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }
}