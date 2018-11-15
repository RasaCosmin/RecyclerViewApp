package com.example.cosmin.recyclerviewapp.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [ListItem::class], version = 1)
abstract class ListItemDatabase : RoomDatabase() {
    abstract fun listItemDao(): ListItemDao
}