package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.myapplication.api.RetrofitBuilder
import com.example.myapplication.common_util.Resource
import com.example.myapplication.model.BillingResp
import com.example.myapplication.model.DcbSubscription
import com.example.myapplication.model.OtpResp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

class BillingViewModel : CommonViewModel() {
    lateinit var mSelectedSubscription: DcbSubscription
    private val TAG: String = BillingViewModel::class.java.name
    var mBillings: BillingResp? = null


    fun fetchBillingDetails(phone: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineHandler) {
            statusLiveData.postValue(Resource.loading(1))
            val result = RetrofitBuilder.getApiService().walletDetails(phone)
            mBillings = handleApiErrorsIfAny(result)
            if (null != mBillings) {
                statusLiveData.postValue(Resource.success(1))
            } else {
                statusLiveData.postValue(Resource.error(1))
            }
        }
    }

    /* fun initPurchase(phone: String) {
         viewModelScope.launch(Dispatchers.IO + coroutineHandler) {
             statusLiveData.postValue(Resource.loading(2))
             val purchase: DcbPurchase = DcbPurchase(
                 phone,
                 mSelectedSubscription.subscriptionName,
                 mSelectedSubscription.subscriptionId,
                 mSelectedSubscription.subscriptionAmount?.toInt()
             )
             val result = RetrofitBuilder.getApiService().purchase(purchase)
             responsePurchase = handleApiErrorsIfAny(result)
             delay(2000)
             if (null != responsePurchase) {
                 statusLiveData.postValue(Resource.success(2))
             } else {
                 statusLiveData.postValue(Resource.error(2))
             }
         }
     }
 */

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

    private fun handleApiErrorsIfAny(response: Response<BillingResp>): BillingResp? {
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


    private fun handleApiErrorsIfAny(response: Response<OtpResp>): OtpResp? {
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