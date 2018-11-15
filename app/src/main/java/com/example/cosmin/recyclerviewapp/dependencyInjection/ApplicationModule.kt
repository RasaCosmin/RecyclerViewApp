package com.example.cosmin.recyclerviewapp.dependencyInjection

import android.app.Application
import com.example.cosmin.recyclerviewapp.RoomDemoApplication
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: RoomDemoApplication) {
    @Provides
    fun provideRoomDemoApplication(): RoomDemoApplication {
        return application
    }

    @Provides
    fun provideApplication(): Application {
        return application
    }
}