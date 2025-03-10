package com.example.myprojectadvance1.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprojectadvance1.data.local.entity.EventEntity
import com.example.myprojectadvance1.data.source.EventRepository
import com.example.myprojectadvance1.data.source.Result
import kotlinx.coroutines.launch

class EventViewModel(private val eventRepository: EventRepository) : ViewModel() {

    fun getHeadlineNews() = eventRepository.getEvents()

    fun getBookmarkedNews() = eventRepository.getFavoriteEvents()

    fun saveNews(event: EventEntity) {
        eventRepository.setFavoriteEvent(event, true)
    }
    fun deleteNews(event: EventEntity) {
        eventRepository.setFavoriteEvent(event, false)
    }
}