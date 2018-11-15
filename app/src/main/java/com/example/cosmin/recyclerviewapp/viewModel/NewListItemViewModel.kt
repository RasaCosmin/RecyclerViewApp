package com.example.cosmin.recyclerviewapp.viewModel

import android.arch.lifecycle.ViewModel
import android.os.AsyncTask
import com.example.cosmin.recyclerviewapp.data.ListItem
import com.example.cosmin.recyclerviewapp.data.ListItemRepository

class NewListItemViewModel(private val repository: ListItemRepository) : ViewModel() {
    fun addNewItemToDatabase(listItem: ListItem){
        AddItemTask().execute(listItem)
    }

    inner class AddItemTask: AsyncTask<ListItem, Unit, Unit>() {
        override fun doInBackground(vararg params: ListItem) {
            repository.insertListItem(params[0])
        }

    }
}