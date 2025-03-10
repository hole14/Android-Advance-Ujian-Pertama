package com.example.myprojectadvance1.di

import android.content.Context
import com.example.myprojectadvance1.data.local.room.EventDatabase
import com.example.myprojectadvance1.data.remote.retrofit.ApiConfig
import com.example.myprojectadvance1.data.source.EventRepository
import com.example.myprojectadvance1.util.AppExecutors

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        val database = EventDatabase.getInstance(context)
        val dao = database.eventDao()
        val appExecutors = AppExecutors()
        return EventRepository.getInstance(apiService, dao, appExecutors)
    }
}