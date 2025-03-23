package com.naveenapps.expensemanager.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.naveenapps.expensemanager.core.designsystem.ui.theme.ExpenseManagerTheme
import com.naveenapps.expensemanager.core.navigation.AppComposeNavigator
import com.naveenapps.expensemanager.core.navigation.ExpenseManagerScreens
import com.naveenapps.expensemanager.core.repository.ActivityComponentProvider

@Composable
fun MainScreen(
    composeNavigator: AppComposeNavigator,
    componentProvider: ActivityComponentProvider,
    isDarkTheme: Boolean,
    landingScreen: ExpenseManagerScreens,
) {
    ExpenseManagerTheme(isDarkTheme = isDarkTheme) {
        val navHostController = rememberNavController()

        LaunchedEffect(Unit) {
            composeNavigator.handleNavigationCommands(navHostController)
        }

        HomePageNavHostContainer(
            componentProvider,
            navHostController,
            landingScreen
        )
    }
}
