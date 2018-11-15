package com.example.cosmin.recyclerviewapp.dependencyInjection

import android.app.Application
import com.example.cosmin.recyclerviewapp.create.CreateFragment
import com.example.cosmin.recyclerviewapp.detail.DetailFragment
import com.example.cosmin.recyclerviewapp.list.ListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RoomModule::class])
interface ApplicationComponent {
    fun inject(listFragment: ListFragment)
    fun inject(createFragment: CreateFragment)
    fun inject(detailFragment: DetailFragment)

    fun application():Application
}