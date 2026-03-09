package com.example.conferenceapp.repository

import com.example.conferenceapp.data.Attendee
import com.example.conferenceapp.data.AttendeeDao
import kotlinx.coroutines.flow.Flow

class AttendeeRepository(private val dao: AttendeeDao) {

    val allAttendees: Flow<List<Attendee>> = dao.getAllAttendees()

    suspend fun insert(attendee: Attendee) {
        dao.insertAttendee(attendee)
    }

    suspend fun update(attendee: Attendee) {
        dao.updateAttendee(attendee)
    }

    suspend fun delete(attendee: Attendee) {
        dao.deleteAttendee(attendee)
    }
}
