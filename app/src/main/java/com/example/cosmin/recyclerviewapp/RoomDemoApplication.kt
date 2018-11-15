package com.example.cosmin.recyclerviewapp

import android.app.Application
import com.example.cosmin.recyclerviewapp.dependencyInjection.ApplicationComponent
import com.example.cosmin.recyclerviewapp.dependencyInjection.ApplicationModule
import com.example.cosmin.recyclerviewapp.dependencyInjection.DaggerApplicationComponent
import com.example.cosmin.recyclerviewapp.dependencyInjection.RoomModule

class RoomDemoApplication : Application() {
    var applicationComponent: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .roomModule(RoomModule(this))
            .build()
    }
}