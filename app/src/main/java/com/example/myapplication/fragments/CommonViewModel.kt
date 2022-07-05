package com.example.myapplication.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.api.RetrofitBuilder
import com.example.myapplication.model.Feed
import com.example.myapplication.utils.Util
import com.example.myapplication.utils.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class CommonViewModel : ViewModel() {
    private val TAG: String = CommonViewModel::class.java.name
    val statusLiveData = MutableLiveData<Resource<Int>>()
    lateinit var mFeedResult: Feed


    fun fetchArticle(tabNum: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            statusLiveData.postValue(Resource.loading(null))
            val response = when (tabNum) {
                0 -> {
                    RetrofitBuilder.getApiService().getTopStories()
                }
                1 -> {
                    RetrofitBuilder.getApiService().getIndiaNews()
                }
                2 -> {
                    RetrofitBuilder.getApiService().getWorldNews()
                }
                else -> {
                    RetrofitBuilder.getApiService().getSportsNews()
                }
            }

            //Log.e(TAG, "${response.body().toString()}")
            handleApiErrorsIfAny(response)?.let {
                mFeedResult = it
                statusLiveData.postValue(Resource.success(Util.CODE_SUCCESS))
            }
        }
    }

    private fun handleApiErrorsIfAny(response: Response<Feed>): Feed? {
        val code: Int = response.code()
        if (code in 200..204) {
            return response.body()
        } else if (code in 400..499) {
            try {
                val jsonObject = JSONObject(response.errorBody()!!.string())
                Log.e("Error ", "" + jsonObject.optString("message"))
                onApiError(jsonObject.optString("message"))
                return null
            } catch (e1: JSONException) {
                e1.printStackTrace()
                onApiError(response.errorBody()!!.string())
                return null
            } catch (e1: IOException) {
                e1.printStackTrace()
                onApiError(e1.localizedMessage)
                return null
            }
        } else if (code in 500..599) {
            onApiError("Something went wrong")
            return null
        }

        onApiError("Something went wrong")
        return null
    }

    private fun onApiError(message: String) {
        statusLiveData.postValue(
            Resource.error(message, Util.CODE_ERROR)
        )
    }

    fun onLoadMore(currentTabNumber: Int) {
        //doubt on paging url
        //don't know which url with what arguments to hit
        statusLiveData.postValue(Resource.loading(Util.CODE_LOAD_MORE))
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            statusLiveData.postValue(Resource.success(Util.CODE_LOAD_MORE))
        }
    }

    val coroutineHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _: CoroutineContext, throwable: Throwable ->
            //sessionLiveData.postValue(Resource.error(throwable.localizedMessage,null))
            throwable.printStackTrace()
            onApiError(throwable.localizedMessage)
        }
}