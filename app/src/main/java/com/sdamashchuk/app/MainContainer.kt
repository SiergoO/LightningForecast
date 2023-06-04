package com.sdamashchuk.app

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.sdamashchuk.app.navigation.AppNavigation
import com.sdamashchuk.app.navigation.NavDestination
import com.sdamashchuk.common.ui.compose.component.TopBar

@Composable
internal fun MainContainer(
    onContainerReady: () -> Unit
) {
    val animatedNavController = rememberAnimatedNavController()
    val navigationIconVisibilityState = rememberSaveable { (mutableStateOf(false)) }
    val screenTitle = rememberSaveable { (mutableStateOf("")) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = screenTitle.value,
                isNavigationIconVisible = navigationIconVisibilityState.value,
                onBackPressed = { animatedNavController.navigateUp() }
            )
        },
        contentColor = MaterialTheme.colorScheme.background
    ) {
        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .padding(it),
            contentColor = MaterialTheme.colorScheme.background
        ) {
            val wasPermissionShown = remember { mutableStateOf(false) }
            val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { wasPermissionShown.value = true }
            )
            if (!wasPermissionShown.value) {
                SideEffect {
                    cameraPermissionResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            } else {
                AppNavigation(
                    navController = animatedNavController,
                    startDestination = NavDestination.Overview.destination,
                    screenTitle,
                    navigationIconVisibilityState
                )
            }
        }
    }
    onContainerReady()
}