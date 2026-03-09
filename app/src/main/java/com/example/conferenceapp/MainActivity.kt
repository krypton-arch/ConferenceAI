package com.example.conferenceapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.conferenceapp.data.Attendee
import com.example.conferenceapp.ui.AddAttendeeScreen
import com.example.conferenceapp.ui.AttendeeListScreen
import com.example.conferenceapp.ui.EditAttendeeScreen
import com.example.conferenceapp.viewmodel.AttendeeViewModel



class MainActivity : ComponentActivity() {

    private val requestSmsPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { /* permission result handled silently */ }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Request SMS permission at launch
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestSmsPermission.launch(Manifest.permission.SEND_SMS)
        }

        setContent {
            val navController = rememberNavController()
            val viewModel: AttendeeViewModel = viewModel()

            // Holds the attendee being edited
            var selectedAttendee by remember { mutableStateOf<Attendee?>(null) }

            NavHost(navController = navController, startDestination = "add") {

                composable("add") {
                    AddAttendeeScreen(
                        viewModel = viewModel,
                        onNavigateToList = { navController.navigate("list") }
                    )
                }

                composable("list") {
                    AttendeeListScreen(
                        viewModel = viewModel,
                        onEditAttendee = { attendee ->
                            selectedAttendee = attendee
                            navController.navigate("edit")
                        },
                        onNavigateToAdd = { navController.navigate("add") }
                    )
                }

                composable("edit") {
                    selectedAttendee?.let { attendee ->
                        EditAttendeeScreen(
                            attendee = attendee,
                            viewModel = viewModel,
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
