package com.example.cosmin.recyclerviewapp.logic

import android.view.View
import com.example.cosmin.recyclerviewapp.data.DataSourceInterface
import com.example.cosmin.recyclerviewapp.data.ListItem
import com.example.cosmin.recyclerviewapp.view.ViewInterface

class Controller(private var view: ViewInterface, private var dataSource: DataSourceInterface) {

    private var tempListItem: ListItem? = null
    private var tempListItemPosition: Int = 0

    init {
        getListFromDataSource()
    }

    fun getListFromDataSource() {
        view.setUpAdapterAndView(dataSource.getListOfData())
    }

    fun onListItemClick(testItem: ListItem, viewRoot: View) {
        view.startDetailActivity(
            testItem.dateAndTime,
            testItem.message,
            testItem.colorResource,
            viewRoot
        )
    }

    fun createNewListItem(){
        val listItem = dataSource.createNewListItem()
        view.addNewListItemToView(listItem)
    }

    fun onListItemSwiped(position: Int, listItem: ListItem?) {
        if(listItem != null){
            dataSource.deleteListItem(listItem)
            view.deleteListItemAt(position)

            tempListItem = listItem
            tempListItemPosition = position

            view.showUndoSnackbar()
        }
    }

    fun onUndoConfirmed() {
        if(tempListItem != null){
            dataSource.insertListItem(tempListItem!!)
            view.insertListItemAt(tempListItemPosition, tempListItem!!)

            tempListItem = null
            tempListItemPosition = 0
        }else{

        }
    }

    fun onSnackBarTimeout() {
        tempListItem = null
        tempListItemPosition = 0
    }
}