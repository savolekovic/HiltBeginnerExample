package com.example.hiltbeginnerexample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.hiltbeginnerexample.R
import com.example.hiltbeginnerexample.model.Blog
import com.example.hiltbeginnerexample.retrofit.BlogRetrofit
import com.example.hiltbeginnerexample.util.DataState
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG : String = "AppDebug"

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetBlogEvents)
    }

    private fun subscribeObservers(){
        viewModel.dataState.observe(this, {
            when(it){
                is DataState.Success<List<Blog>> ->{
                    displayProgressBar(false)
                    appendBlogTitles(it.data)
                }
                is DataState.Error->{
                    displayProgressBar(false)
                    displayError(it.exception.message)
                }
                is DataState.Loading ->{
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun displayError(message: String?){
        if(message != null){
            text.text = message
        }else{
            text.text = "Unknown error."
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean){
        loading.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }

    private fun appendBlogTitles(blogs: List<Blog>){
        val sb = StringBuilder()
        for(blog in blogs){
            sb.append(blog.title+ "\n")
        }
        text.text = sb.toString()
    }
}