package com.example.wiprointerview.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wiprointerview.application.AppApplication
import com.example.wiprointerview.model.ItemResult
import com.example.wiprointerview.network.Result
import com.example.wiprointerview.repository.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainViewModel : ViewModel(),CoroutineScope {

    var mItemListData: MutableLiveData<Result<ItemResult?>> = MutableLiveData()

    private var mJob: Job = Job()
    @Inject
    lateinit var mRepository : MainRepository
    override val coroutineContext: CoroutineContext get() = mJob + Dispatchers.Main

    init {
        AppApplication.getApplication().dataComponent.inject(this)
        callApi()
    }

    fun callApi() {
        launch(context = coroutineContext) {
            getData()
        }
    }

    private fun getData() {
        mItemListData.value = Result.loading()
         mRepository.getData(mItemListData)
    }

    fun jobCancel() {
        mJob.cancel()
    }

}