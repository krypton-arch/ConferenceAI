package com.example.conferenceapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.conferenceapp.viewmodel.AttendeeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAttendeeScreen(
    viewModel: AttendeeViewModel,
    onNavigateToList: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("CHRIST University — AI Conference") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Register New Attendee",
                style = MaterialTheme.typography.headlineSmall
            )

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
                        phone.isBlank() -> errorMessage = "Phone number cannot be empty."
                        else -> {
                            errorMessage = ""
                            viewModel.addAttendee(name.trim(), ageInt, phone.trim())
                            name = ""; age = ""; phone = ""
                            onNavigateToList()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register & Send SMS")
            }

            OutlinedButton(
                onClick = onNavigateToList,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View All Attendees")
            }
        }
    }
}
