package com.example.cosmin.recyclerviewapp.data

interface DataSourceInterface {
    fun getListOfData(): MutableList<ListItem>
    fun createNewListItem(): ListItem
    fun deleteListItem(listItem: ListItem)
    fun insertListItem(tempListItem: ListItem)
}