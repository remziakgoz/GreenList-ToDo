package com.remziakgoz.todolistwithcompose.presentation

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.remziakgoz.todolistwithcompose.presentation.ui.theme.ToDoListWithComposeTheme
import com.remziakgoz.todolistwithcompose.presentation.views.AddItemScreen
import com.remziakgoz.todolistwithcompose.presentation.views.ItemList
import com.remziakgoz.todolistwithcompose.presentation.views.ItemViewModel
import com.remziakgoz.todolistwithcompose.util.NotificationHelper
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel by viewModel<ItemViewModel>()
    private var isPermissionDeniedPermanently by mutableStateOf(false)
    private lateinit var notificationHelper: NotificationHelper

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                isPermissionDeniedPermanently = true
            } else {
                initializeNotifications()
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        notificationHelper = NotificationHelper(this)
        askNotificationPermission()
        viewModel.getItemList()
        
        setContent {
            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()

            ToDoListWithComposeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.ListScreen.route
                        ) {
                            composable(route = Screen.ListScreen.route) {
                                viewModel.getItemList()
                                ItemList(
                                    navController = navController,
                                    viewModel = viewModel
                                )
                            }

                            composable(route = Screen.ListAddScreen.route) {
                                AddItemScreen { item ->
                                    viewModel.saveItem(item)
                                    navController.navigate(route = Screen.ListScreen.route) {
                                        popUpTo(Screen.ListScreen.route) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Show Snackbar when permission is denied
                    LaunchedEffect(isPermissionDeniedPermanently) {
                        if (isPermissionDeniedPermanently) {
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Notifications are disabled. Enable them in settings?",
                                    actionLabel = "Settings",
                                    duration = SnackbarDuration.Long
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    openAppSettings()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun openAppSettings() {
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            initializeNotifications()
            return
        }

        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                initializeNotifications()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                isPermissionDeniedPermanently = true
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun initializeNotifications() {
        viewModel.checkUncompleted(this) { hasUncompleted ->
            if (hasUncompleted) {
                notificationHelper.scheduleNotificationAtIntervals(4 * 60 * 60 * 1000L, 0L)
            } else {
                notificationHelper.cancelScheduledNotifications()
            }
        }
    }
}