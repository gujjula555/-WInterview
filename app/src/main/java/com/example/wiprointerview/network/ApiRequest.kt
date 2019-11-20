package com.example.wiprointerview.network

import com.example.wiprointerview.model.ItemResult

import retrofit2.Call
import retrofit2.http.GET

interface ApiRequest {
    @get:GET("/s/2iodh4vg0eortkl/facts.json")
    val data: Call<ItemResult>
}