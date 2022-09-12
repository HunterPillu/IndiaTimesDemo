package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.api.RetrofitBuilder
import com.example.myapplication.model.Feed
import com.example.myapplication.common_util.Util
import com.example.myapplication.common_util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class SplashViewModel : CommonViewModel() {
    private val TAG: String = SplashViewModel::class.java.name


}