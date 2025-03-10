package com.example.myprojectadvance1.data.remote.retrofit

import com.example.myprojectadvance1.data.remote.respone.ListEventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("events")
    fun getListEvent(): Call<ListEventResponse>

    @GET("events/{id}")
    fun getDetailEvent(@Path("id") id: String): ListEventResponse

    @GET("events?active=-1&q=devcoach")
    fun getSearchEvent(@Path("query") query: String): ListEventResponse

    @GET("events?active=1")
    fun getUpcomingEvent(): ListEventResponse

    @GET("events?active=0")
    fun getFinishedEvent(): ListEventResponse


}