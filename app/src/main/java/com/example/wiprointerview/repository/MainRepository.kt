package com.example.wiprointerview.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.wiprointerview.model.ItemResult
import com.example.wiprointerview.network.ApiRequest
import com.example.wiprointerview.network.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(var apiRequest: ApiRequest?) {
    fun getData(mItemListData :MutableLiveData<Result<ItemResult?>>) {
         apiRequest?.data?.enqueue(object : Callback<ItemResult> {
            override fun onFailure(call: Call<ItemResult>, t: Throwable) {
                mItemListData.value = t.message?.let { Result.error(it, null) }
            }
            override fun onResponse(call: Call<ItemResult>, response: Response<ItemResult>) {
               mItemListData.value = Result.success(response.body())
            }
        })
    }
}