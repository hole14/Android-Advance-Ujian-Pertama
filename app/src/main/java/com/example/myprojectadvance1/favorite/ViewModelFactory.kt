package com.example.myprojectadvance1.favorite

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myprojectadvance1.data.source.EventRepository
import com.example.myprojectadvance1.di.Injection

class ViewModelFactory private constructor(private val newsRepository: EventRepository) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java)){
            return EventViewModel(newsRepository) as T
        }
        throw IllegalArgumentException("Unknouwn ViewModel class" + modelClass.name)
    }
    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }

    }
}