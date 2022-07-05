package com.example.myapplication.fragments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.api.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TopStoriesViewModel : ViewModel() {


    fun fetchTopStories() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = RetrofitBuilder.getApiService().getTopStories()
            Log.e("prinkal", "${result.body().toString()}")

        }
    }
}