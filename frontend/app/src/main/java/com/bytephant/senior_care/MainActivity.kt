package com.bytephant.senior_care

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bytephant.senior_care.ui.routing.AppScreenType
import com.bytephant.senior_care.ui.routing.TopBar
import com.bytephant.senior_care.ui.theme.SeniorcareTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SeniorcareTheme {
                val navController : NavHostController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentScreen = AppScreenType.valueOf(
                    backStackEntry?.destination?.route ?: AppScreenType.CHAT.name
                )
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

                        }
                    }
                }
            }
        }
    }
}
