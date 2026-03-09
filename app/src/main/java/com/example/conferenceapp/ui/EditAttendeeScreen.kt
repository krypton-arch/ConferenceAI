package com.example.conferenceapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.conferenceapp.data.Attendee
import com.example.conferenceapp.viewmodel.AttendeeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAttendeeScreen(
    attendee: Attendee,
    viewModel: AttendeeViewModel,
    onNavigateBack: () -> Unit
) {
    var name by remember { mutableStateOf(attendee.name) }
    var age by remember { mutableStateOf(attendee.age.toString()) }
    var phone by remember { mutableStateOf(attendee.phone) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Edit Attendee") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Update Attendee Details", style = MaterialTheme.typography.headlineSmall)

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Age") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            if (errorMessage.isNotBlank()) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = {
                    val ageInt = age.toIntOrNull()
                    when {
                        name.isBlank() -> errorMessage = "Name cannot be empty."
                        ageInt == null || ageInt <= 0 -> errorMessage = "Enter a valid age."
                        phone.isBlank() -> errorMessage = "Phone cannot be empty."
                        else -> {
                            viewModel.updateAttendee(
                                attendee.copy(
                                    name = name.trim(),
                                    age = ageInt,
                                    phone = phone.trim()
                                )
                            )
                            onNavigateBack()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }

            OutlinedButton(
                onClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel")
            }
        }
    }
}
