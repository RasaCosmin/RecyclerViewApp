package com.example.cosmin.recyclerviewapp.dependencyInjection

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import com.example.cosmin.recyclerviewapp.data.ListItemDao
import com.example.cosmin.recyclerviewapp.data.ListItemDatabase
import com.example.cosmin.recyclerviewapp.data.ListItemRepository
import com.example.cosmin.recyclerviewapp.viewModel.CustomViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(application: Application) {
    private var database: ListItemDatabase = Room.databaseBuilder(
        application,
        ListItemDatabase::class.java,
        "ListItem.db"
    ).build()

    @Provides
    @Singleton
    fun provideListItemRepository(listItemDao: ListItemDao): ListItemRepository {
        return ListItemRepository(listItemDao)
    }

    @Provides
    @Singleton
    fun provideListItemDao(database: ListItemDatabase): ListItemDao {
        return database.listItemDao()
    }

    @Provides
    @Singleton
    fun provideListItemDatabase(): ListItemDatabase {
        return database
    }

    @Provides
    @Singleton
    fun provideViewModelFactory(repository: ListItemRepository): ViewModelProvider.Factory{
        return CustomViewModelFactory(repository)
    }
}