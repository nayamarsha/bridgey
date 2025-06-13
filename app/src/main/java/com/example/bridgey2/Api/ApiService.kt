package com.example.bridgey2.Api
import com.example.bridgey2.Models.ResponseEvent
import com.example.bridgey2.Models.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("event")
    fun getProducts(): Call<List<ResponseEvent>>

    @GET("event")
    fun getEvents(): Call<List<SearchResult>>

    @GET("sponsor")
    fun getSponsors(): Call<List<SearchResult>>

    @GET("tenant")
    fun getTenants(): Call<List<SearchResult>>

}
