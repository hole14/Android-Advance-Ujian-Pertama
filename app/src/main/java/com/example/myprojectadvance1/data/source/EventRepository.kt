package com.example.myprojectadvance1.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.myprojectadvance1.data.local.entity.EventEntity
import com.example.myprojectadvance1.data.local.room.EventDao
import com.example.myprojectadvance1.data.remote.respone.ListEventResponse
import com.example.myprojectadvance1.data.remote.retrofit.ApiConfig
import com.example.myprojectadvance1.data.remote.retrofit.ApiService
import com.example.myprojectadvance1.util.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventRepository private constructor(
    private val apiService: ApiService,
    private val eventDao: EventDao,
    private val appExecutors: AppExecutors
) {
    val result = MediatorLiveData<Result<List<EventEntity>>>()
    fun getEvents(): LiveData<Result<List<EventEntity>>> {
        result.value = Result.Loading
        val client = apiService.getListEvent()
        client.enqueue(object : Callback<ListEventResponse> {
            override fun onFailure(call: Call<ListEventResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }

            override fun onResponse(
                call: Call<ListEventResponse>,
                response: Response<ListEventResponse>
            ) {
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents
                    val eventList = ArrayList<EventEntity>()
                    appExecutors.diskIO.execute {
                        events?.forEach { event ->
                            val isFavorited = eventDao.isEventFavorited(event.id)
                            val eventEntity = EventEntity(
                                event.id,
                                event.name,
                                event.summary,
                                event.mediaCover,
                                event.registrants,
                                event.imageLogo,
                                event.link,
                                event.description,
                                event.ownerName,
                                event.cityName,
                                event.quota,
                                event.beginTime,
                                event.endTime,
                                event.category,
                                isFavorited
                            )
                            eventList.add(eventEntity)
                        }
                        eventDao.deleteAll()
                        eventDao.insertEvents(eventList)
                    }
                }
            }

        })
        val localData = eventDao.getTopRatedEvents()
        result.addSource(localData) { newData: List<EventEntity> ->
            result.value = Result.Success(newData)
        }
        return result
    }

    fun getFavoriteEvents(): LiveData<List<EventEntity>> {
        return eventDao.getEventFavorites()
    }
    fun setFavoriteEvent(event: EventEntity, favoriteState: Boolean) {
        appExecutors.diskIO.execute {
            event.isFavorited = favoriteState
            eventDao.updateEvent(event)
        }
    }

    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            apiService: ApiService,
            eventDao: EventDao,
            appExecutors: AppExecutors
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(apiService, eventDao, appExecutors)
            }.also { instance = it }
    }
}