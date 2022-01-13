package com.example.hiltbeginnerexample.di

import com.example.hiltbeginnerexample.repository.MainRepository
import com.example.hiltbeginnerexample.retrofit.BlogRetrofit
import com.example.hiltbeginnerexample.retrofit.NetworkMapper
import com.example.hiltbeginnerexample.room.BlogDao
import com.example.hiltbeginnerexample.room.CacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        blogDao: BlogDao,
        blogRetrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): MainRepository{
        return MainRepository(blogDao, blogRetrofit, cacheMapper, networkMapper)
    }
}