package com.example.conferenceapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attendee_table")
data class Attendee(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val age: Int,
    val phone: String
)
