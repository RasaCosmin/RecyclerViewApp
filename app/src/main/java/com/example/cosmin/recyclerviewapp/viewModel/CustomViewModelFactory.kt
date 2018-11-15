package com.example.cosmin.recyclerviewapp.viewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.cosmin.recyclerviewapp.data.ListItemRepository

class CustomViewModelFactory(private val repository: ListItemRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return when {
            modelClass.isAssignableFrom(ListItemCollectionViewModel::class.java) -> ListItemCollectionViewModel(
                repository
            ) as T
            modelClass.isAssignableFrom(ListItemViewModel::class.java) -> ListItemViewModel(repository) as T
            modelClass.isAssignableFrom(NewListItemViewModel::class.java) -> NewListItemViewModel(repository) as T
            else -> throw IllegalAccessException("ViewModel not found")
        }
    }
}