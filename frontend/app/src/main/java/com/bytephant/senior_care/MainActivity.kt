package com.bytephant.senior_care

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bytephant.senior_care.background.enrollLocationSaver
import com.bytephant.senior_care.ui.routing.AppScreenType
import com.bytephant.senior_care.ui.routing.TopBar
import com.bytephant.senior_care.ui.screen.chat.ChatScreen
import com.bytephant.senior_care.ui.screen.chat.ChatViewModel
import com.bytephant.senior_care.ui.theme.SeniorcareTheme

class MainActivity : ComponentActivity() {
    fun requestPermissions() {
        val neededPermissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            Manifest.permission.INTERNET,
        )
        ActivityCompat.requestPermissions(this, neededPermissions, 1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()
        enrollLocationSaver(application)
        enableEdgeToEdge()
        setContent {
            SeniorcareTheme {
                val navController : NavHostController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentScreen = AppScreenType.valueOf(
                    backStackEntry?.destination?.route ?: AppScreenType.CHAT.name
                )
                val chatViewModel: ChatViewModel= viewModel(factory = ChatViewModel.Factory)
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBar(currentScreenType = currentScreen)
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = AppScreenType.CHAT.name,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = AppScreenType.CHAT.name) {
                            ChatScreen(chatViewModel, Modifier.padding(innerPadding))
                        }
                    }
                }
            }
        }
    }
}
