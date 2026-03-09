package com.example.conferenceapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendeeDao {

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendee(attendee: Attendee)

    // READ - returns a live stream; UI auto-updates when data changes
    @Query("SELECT * FROM attendee_table ORDER BY name ASC")
    fun getAllAttendees(): Flow<List<Attendee>>

    // UPDATE
    @Update
    suspend fun updateAttendee(attendee: Attendee)

    // DELETE
    @Delete
    suspend fun deleteAttendee(attendee: Attendee)
}
