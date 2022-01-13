package com.example.hiltbeginnerexample.repository

import com.example.hiltbeginnerexample.retrofit.BlogRetrofit
import com.example.hiltbeginnerexample.retrofit.NetworkMapper
import com.example.hiltbeginnerexample.room.BlogDao
import com.example.hiltbeginnerexample.room.CacheMapper
import com.example.hiltbeginnerexample.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class MainRepository
constructor(
    private val blogDao: BlogDao,
    private val blogRetrofit: BlogRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
){
    suspend fun getBlogs() = flow{
        emit(DataState.Loading)
        delay(1000)
        try{
            val networkBlogs = blogRetrofit.getBlogs()
            val blogs = networkMapper.mapFromEntityList(networkBlogs)
            for(blog in blogs){
                blogDao.insertBlog(
                    cacheMapper.mapToEntity(blog)
                )
            }
            val cachedBlogs = blogDao.getBlogs()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedBlogs)))
        }catch(e: Exception){
            emit(DataState.Error(e))
        }
    }
}