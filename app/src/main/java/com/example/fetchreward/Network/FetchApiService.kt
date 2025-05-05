package com.example.fetchreward.Network

import com.example.fetchreward.modal.Item
import retrofit2.http.GET

interface FetchApiService {
    @GET("hiring.json")
    suspend fun getItems(): List<Item>
}