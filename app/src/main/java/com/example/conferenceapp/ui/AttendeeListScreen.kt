package com.example.conferenceapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.conferenceapp.data.Attendee
import com.example.conferenceapp.viewmodel.AttendeeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendeeListScreen(
    viewModel: AttendeeViewModel,
    onEditAttendee: (Attendee) -> Unit,
    onNavigateToAdd: () -> Unit
) {
    val attendees by viewModel.allAttendees.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("All Registered Attendees") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAdd) {
                Text("+", style = MaterialTheme.typography.headlineSmall)
            }
        }
    ) { padding ->
        if (attendees.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No attendees registered yet.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(attendees) { attendee ->
                    AttendeeCard(
                        attendee = attendee,
                        onEdit = { onEditAttendee(attendee) },
                        onDelete = { viewModel.deleteAttendee(attendee) }
                    )
                }
            }
        }
    }
}

@Composable
fun AttendeeCard(
    attendee: Attendee,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Attendee") },
            text = { Text("Are you sure you want to remove ${attendee.name}?") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete()
                    showDeleteDialog = false
                }) { Text("Delete", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Cancel") }
            }
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = attendee.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "Age: ${attendee.age}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Phone: ${attendee.phone}", style = MaterialTheme.typography.bodySmall)
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
