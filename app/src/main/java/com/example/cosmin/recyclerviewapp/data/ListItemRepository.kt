package com.example.cosmin.recyclerviewapp.data

import android.arch.lifecycle.LiveData
import javax.inject.Inject

class ListItemRepository @Inject constructor(private val listItemDao: ListItemDao) {
    fun getListOfData(): LiveData<MutableList<ListItem>> {
        return listItemDao.getListItems()
    }

    fun getListItem(itemId: String): LiveData<ListItem> {
        return listItemDao.getListItemById(itemId)
    }

    fun deleteListIte(listItem: ListItem){
        listItemDao.deleteListItem(listItem)
    }

    fun insertListItem(listItem: ListItem):Long{
        return listItemDao.inserListItem(listItem);
    }
}