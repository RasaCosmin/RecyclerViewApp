package com.example.cosmin.recyclerviewapp.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.os.AsyncTask
import com.example.cosmin.recyclerviewapp.data.ListItem
import com.example.cosmin.recyclerviewapp.data.ListItemRepository
import javax.inject.Inject

class ListItemCollectionViewModel(private val repository: ListItemRepository) : ViewModel() {

    fun getListItems(): LiveData<MutableList<ListItem>> {
        return repository.getListOfData()
    }

    fun deleteListItem(listItem: ListItem) {
        val task = DeleteItemTask()
        task.execute(listItem)
    }

    inner class DeleteItemTask : AsyncTask<ListItem, Unit, Unit>() {
        override fun doInBackground(vararg params: ListItem) {
            repository.deleteListIte(params[0])
        }
    }
}