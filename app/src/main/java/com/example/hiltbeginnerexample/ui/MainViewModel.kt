package com.example.hiltbeginnerexample.ui

import androidx.lifecycle.*
import com.example.hiltbeginnerexample.model.Blog
import com.example.hiltbeginnerexample.repository.MainRepository
import com.example.hiltbeginnerexample.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val mainRepository: MainRepository
): ViewModel(){

    private val _dataState: MutableLiveData<DataState<List<Blog>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Blog>>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent){
        viewModelScope.launch {
            when(mainStateEvent){
                is MainStateEvent.GetBlogEvents ->{
                    mainRepository.getBlogs()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is MainStateEvent.None ->{
                    //nothing
                }
            }
        }
    }
}

sealed class MainStateEvent{
    object GetBlogEvents: MainStateEvent()
    object None: MainStateEvent()
}