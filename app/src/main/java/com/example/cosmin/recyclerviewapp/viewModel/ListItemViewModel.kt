package com.example.cosmin.recyclerviewapp.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.cosmin.recyclerviewapp.data.ListItem
import com.example.cosmin.recyclerviewapp.data.ListItemRepository

class ListItemViewModel(private val repository: ListItemRepository) : ViewModel() {
    fun getListItemById(itemId: String): LiveData<ListItem> {
        return repository.getListItem(itemId)
    }
}