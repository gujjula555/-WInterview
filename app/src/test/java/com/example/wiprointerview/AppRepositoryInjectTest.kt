package com.example.wiprointerview

import androidx.lifecycle.MutableLiveData
import com.example.wiprointerview.application.AppApplication
import com.example.wiprointerview.model.ItemResult
import com.example.wiprointerview.network.ApiRequest
import com.example.wiprointerview.network.Result
import com.example.wiprointerview.repository.MainRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import javax.inject.Inject


class AppRepositoryInjectTest {
    @Inject
    lateinit var apiRequest: ApiRequest

    @Inject
    lateinit var mRepository: MainRepository

    val mItemListData: MutableLiveData<Result<ItemResult?>> = MutableLiveData()

    @Before
    fun setUp() {
        val component = DaggerDataComponentTest.builder()
            .dataModule(TestApplicationModule(AppApplication()))
            .build()
        component.inject(this)
    }

    @Test
    fun `my test`() {
        Assert.assertNotNull(apiRequest)
        //apiRequest.data.execute()
    }

    @Test
    fun testApi() {
        //  mRepository.getData(mItemListData)
        //Assert.assertTrue(mItemListData.value?.status == Result.Status.SUCCESS)
    }
}