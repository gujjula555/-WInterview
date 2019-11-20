package com.example.wiprointerview.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.wiprointerview.model.ItemResult
import com.example.wiprointerview.network.ApiRequest
import com.example.wiprointerview.network.Result
import com.example.wiprointerview.network.RetrofitRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class MainViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
     var data: MutableLiveData<Result<ItemResult?>> = MutableLiveData();
    private val context = getApplication<Application>().applicationContext
    private var job: Job = Job();
    private var apiRequest: ApiRequest?
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    init {
         apiRequest = RetrofitRequest.retrofitInstance?.create(ApiRequest::class.java)

        callApi()
    }
    fun callApi(){
        launch(context = coroutineContext) {
            getData(apiRequest)
        }
    }

    suspend fun getData(apiRequest: ApiRequest?) {
        data.value = Result.loading()
        if (isConnectingToInternet(context)) {
            apiRequest?.data?.enqueue(object : Callback<ItemResult> {
                override fun onFailure(call: Call<ItemResult>, t: Throwable) {
                    data.value = t.message?.let { Result.error(it, null) }
                }
                override fun onResponse(call: Call<ItemResult>, response: Response<ItemResult>) {
                    Log.d("result", response.body().toString())
                    data.value = Result.success(response.body())
                }

            });
        }else{
            data.value = Result.error("Net is not available", null)
        }

    }


    fun jobCancel() {
        job.cancel()
    }

    fun isConnectingToInternet(context: Context): Boolean {
        val connectivity = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (connectivity != null) {
            val info = connectivity.allNetworkInfo
            if (info != null)
                for (i in info)
                    if (i.state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
        }
        return false
    }
}