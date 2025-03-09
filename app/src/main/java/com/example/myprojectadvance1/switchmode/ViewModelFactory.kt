package com.example.myprojectadvance1.switchmode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val pref: SettingPreferences): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHEKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)){
            return SettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)

    }
}