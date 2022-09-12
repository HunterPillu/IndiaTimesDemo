package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.myapplication.api.RetrofitBuilder
import com.example.myapplication.common_util.Resource
import com.example.myapplication.model.DCBAuth
import com.example.myapplication.model.DcbSubscription
import com.example.myapplication.model.Feed
import com.example.myapplication.model.OtpResp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

class SubscriptionViewModel : CommonViewModel() {
    private val TAG: String = SubscriptionViewModel::class.java.name
    var mSubscriptions: MutableList<DcbSubscription>? = null
    var responseOTP: OtpResp? = null


    fun fetchSubscription() {
        viewModelScope.launch(Dispatchers.IO + coroutineHandler) {
            statusLiveData.postValue(Resource.loading(1))
            val result = RetrofitBuilder.getApiService().subscriptionDetails()
            mSubscriptions = handleApiErrorsIfAny(result)
            if (null != mSubscriptions) {
                statusLiveData.postValue(Resource.success(1))
            } else {
                statusLiveData.postValue(Resource.error(1))
            }
        }
    }


    /*fun initAssociate(otp: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineHandler) {
            statusLiveData.postValue(Resource.loading(null))
            val request: DCBAuth = DCBAuth(
                responseDCB?.email,
                null,
                responseDCB?.associationId,
                responseDCB?.requestId,
                responseDCB?.gpToken,
                otp
            )
            val result = RetrofitBuilder.getApiService().association(request)
            responseOTP = handleApiErrorsIfAny(result)
            if (null != responseOTP) {
                statusLiveData.postValue(Resource.success(2))
            } else {
                statusLiveData.postValue(Resource.error(2))
            }
        }
    }*/

    private fun handleApiErrorsIfAny(response: Response<MutableList<DcbSubscription>>): MutableList<DcbSubscription>? {
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


    private fun handleApiErrorsIfAny(response: Response<DCBAuth>): DCBAuth? {
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

}