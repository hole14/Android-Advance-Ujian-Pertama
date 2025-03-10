package com.example.myprojectadvance1.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myprojectadvance1.data.local.entity.EventEntity

@Dao
interface EventDao {
    @Query("SELECT * FROM event ORDER BY beginTime DESC")
    fun getEvents(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM event WHERE category = 'finished' ORDER BY endTime DESC")
    fun getFinishedEvents(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM event WHERE category = 'upcoming' ORDER BY beginTime DESC")
    fun getUpcomingEvents(): LiveData<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertEvents(events: List<EventEntity>)

    @Query("SELECT * FROM event ORDER BY registrants DESC LIMIT 10")
    fun getTopRatedEvents(): LiveData<List<EventEntity>>

    @Update
    fun updateEvent(event: EventEntity)

    @Query("DELETE FROM event WHERE isFavorited = 0")
    fun deleteAll()

    @Query("SELECT * FROM event where isFavorited = 1")
    fun getEventFavorites(): LiveData<List<EventEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM event WHERE id = :id AND isFavorited = 1)")
    fun isEventFavorited(id: Int): Boolean
}