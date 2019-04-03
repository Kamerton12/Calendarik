package com.example.calendarik.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface CalendarDAO{
    @Insert
    fun insert(vararg calendars: Calendar)

    @Query("SELECT * FROM Calendar")
    fun getAll(): List<Calendar>

    @Query("SELECT * FROM Calendar WHERE date >= :from AND date <= :to")
    fun getPeriod(from: Long, to: Long): List<Calendar>

    @Delete
    fun delete(date: Calendar)
}