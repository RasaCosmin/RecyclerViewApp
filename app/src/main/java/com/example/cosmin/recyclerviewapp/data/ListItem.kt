package com.example.cosmin.recyclerviewapp.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class ListItem(
    @PrimaryKey var dateAndTime: String,
    var message: String,
    var colorResource: Int)