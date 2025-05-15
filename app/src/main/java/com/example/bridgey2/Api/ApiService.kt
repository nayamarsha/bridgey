package com.example.bridgey2.Api
import com.example.bridgey2.Models.ResponseEvent
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("event")
    fun getProducts(): Call<List<ResponseEvent>>
}
