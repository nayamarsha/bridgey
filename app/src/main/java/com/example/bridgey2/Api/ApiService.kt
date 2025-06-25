package com.example.bridgey2.Api
import com.example.bridgey2.Models.ResponseEvent
import com.example.bridgey2.Models.ResponseSponsor
import com.example.bridgey2.Models.SearchEvent
import com.example.bridgey2.Models.SearchSponsor
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("event")
    fun getProducts(): Call<List<ResponseEvent>>

    @GET("sponsor")
    fun getSponsorsOnly(): Call<List<ResponseSponsor>>


    @GET("event")
    fun getEvents(): Call<List<SearchEvent>>

    @GET("sponsor")
    fun getSponsors(): Call<List<SearchSponsor>>

    @GET("tenant")
    fun getTenants(): Call<List<SearchEvent>>

}
