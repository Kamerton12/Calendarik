package com.example.calendarik.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Calendar(
    @PrimaryKey
    var date: Long
)