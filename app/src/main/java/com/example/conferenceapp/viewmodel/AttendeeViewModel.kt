package com.example.conferenceapp.viewmodel

import android.app.Application
import android.telephony.SmsManager
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.conferenceapp.data.Attendee
import com.example.conferenceapp.data.AttendeeDatabase
import com.example.conferenceapp.repository.AttendeeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AttendeeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AttendeeRepository
    val allAttendees: StateFlow<List<Attendee>>

    init {
        val db = AttendeeDatabase.getDatabase(application)
        repository = AttendeeRepository(db.attendeeDao())
        allAttendees = repository.allAttendees.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    // CREATE + SMS
    fun addAttendee(name: String, age: Int, phone: String) {
        if (name.isBlank() || phone.isBlank()) return
        val attendee = Attendee(name = name, age = age, phone = phone)
        viewModelScope.launch {
            repository.insert(attendee)
            sendSmsConfirmation(phone, name)
        }
    }

    // UPDATE
    fun updateAttendee(attendee: Attendee) {
        viewModelScope.launch {
            repository.update(attendee)
        }
    }

    // DELETE
    fun deleteAttendee(attendee: Attendee) {
        viewModelScope.launch {
            repository.delete(attendee)
        }
    }

    // SMS Helper
    private fun sendSmsConfirmation(phone: String, name: String) {
        try {
            val message = "Welcome to the AI Conference, $name! We look forward to seeing you."
            val smsManager = getApplication<Application>()
                .getSystemService(SmsManager::class.java)
            smsManager?.sendTextMessage(phone, null, message, null, null)
            Toast.makeText(
                getApplication(),
                "SMS sent to $phone",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: Exception) {
            Toast.makeText(
                getApplication(),
                "SMS failed: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
