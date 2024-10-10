package com.bytephant.senior_care

import android.Manifest
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.bytephant.senior_care.application.ADBCustomReceiver
import com.bytephant.senior_care.application.SeniorCareApplication
import com.bytephant.senior_care.background.enrollLocationSaver
import com.bytephant.senior_care.background.worker.LocationSaver
import com.bytephant.senior_care.domain.data.UserLocationStatus
import com.bytephant.senior_care.ui.routing.AppScreenType
import com.bytephant.senior_care.ui.routing.TopBar
import com.bytephant.senior_care.ui.screen.chat.ChatScreen
import com.bytephant.senior_care.ui.screen.chat.ChatViewModel
import com.bytephant.senior_care.ui.screen.home.HomeScreen
import com.bytephant.senior_care.ui.screen.home.HomeViewModel
import com.bytephant.senior_care.ui.theme.SeniorcareTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val receiver: ADBCustomReceiver = ADBCustomReceiver()

    fun requestPermissions() {
        val neededPermissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            Manifest.permission.FOREGROUND_SERVICE_LOCATION,
            Manifest.permission.INTERNET,
        )
        ActivityCompat.requestPermissions(this, neededPermissions, 1)
    }

    fun enrollADBReceiver() {
        registerReceiver(
            receiver,
            IntentFilter("com.example.action.INIT_TALKING"),
            RECEIVER_EXPORTED
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()
        val workRequest = OneTimeWorkRequest
            .Builder(LocationSaver::class.java)
            .build()
        WorkManager.getInstance(this).enqueue(workRequest)
        WorkManager.getInstance(this)
            .getWorkInfoByIdLiveData(workRequest.id)
            .observe(this, Observer { workInfo ->
                if (workInfo != null && workInfo.state.isFinished) {
                    when (workInfo.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            val outputData = workInfo.outputData
                            val resultData = outputData.getString("RESULT_DATA")
                            Log.d("WorkManager", "작업 성공: $resultData")
                        }
                        WorkInfo.State.FAILED -> {
                            val outputData = workInfo.outputData
                            val errorMessage = outputData.getString("ERROR_MESSAGE")
                            val init = outputData.getString("init")
                            val permission = outputData.getString("permissions")
                            val location = outputData.getString("location")
                            Log.e("WorkManager", "작업 실패:\n" +
                                    "초기화 : $init\n" +
                                    "권한: $permission\n" +
                                    "위치 : $location\n" +
                                    "message: $errorMessage")
                        }
                        else -> {
                            // 다른 상태 처리
                        }
                    }
                }
            })

        enrollLocationSaver(application)
        enrollADBReceiver()
        enableEdgeToEdge()
        setContent {
            SeniorcareTheme {
                val navController : NavHostController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentScreen = AppScreenType.valueOf(
                    backStackEntry?.destination?.route ?: AppScreenType.CHAT.name
                )
                val chatViewModel: ChatViewModel= viewModel(factory = ChatViewModel.Factory)
                val homeViewModel: HomeViewModel= viewModel(factory = HomeViewModel.Factory)
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBar(
                            currentScreenType = currentScreen,
                            canNavigateBack = navController.previousBackStackEntry != null,
                            navigateUp ={ navController.navigateUp() }
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = AppScreenType.HOME.name,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = AppScreenType.CHAT.name) {
                            ChatScreen(chatViewModel)
                        }
                        composable(route= AppScreenType.HOME.name) {
                            HomeScreen(
                                homeViewModel,
                                { navController.navigate(AppScreenType.CHAT.name) }
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}
