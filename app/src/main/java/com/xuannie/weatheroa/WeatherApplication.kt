package com.xuannie.weatheroa

import android.app.Application
import com.xuannie.weatheroa.ui.data.AppContainer
import com.xuannie.weatheroa.ui.data.DefaultAppContainer

class WeatherApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}