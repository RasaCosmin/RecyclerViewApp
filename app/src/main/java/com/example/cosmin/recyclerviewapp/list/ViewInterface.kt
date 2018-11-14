package com.example.cosmin.recyclerviewapp.list

import android.view.View
import com.example.cosmin.recyclerviewapp.data.ListItem

interface ViewInterface {

    fun startDetailActivity(dateAndTime: String, message: String, colorResource: Int, viewRoot: View)
    fun setUpAdapterAndView(listOfData: MutableList<ListItem>?)
    fun addNewListItemToView(listItem: ListItem)
    fun deleteListItemAt(position: Int)
    fun showUndoSnackbar()
    fun insertListItemAt(position: Int, ListItem: ListItem)
}