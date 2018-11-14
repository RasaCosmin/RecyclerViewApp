package com.example.cosmin.recyclerviewapp.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface ListItemDao {

    @Query("SELECT * FROM ListItem")
    fun getListItems(): LiveData<MutableList<ListItem>>

    @Query("SELECT * FROM ListItem WHERE itemId = :itemId")
    fun getListItemById(itemId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserListItem(listItem: ListItem): Long

    @Delete
    fun deleteListItem(listItem: ListItem)

}