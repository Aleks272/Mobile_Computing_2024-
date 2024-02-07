package com.example.composetutorial

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composetutorial.ConversationScreen
import com.example.composetutorial.HomeScreen
import com.example.composetutorial.SampleData
import com.example.composetutorial.UserViewModel

@Composable
fun AppNavigation(viewModel: UserViewModel){
    val navController = rememberNavController()

    NavHost(
    navController = navController,
    startDestination = "Home"
    ) {
        composable("Home") {
            HomeScreen(navController, viewModel)
        }
        composable("Conversation") {
            ConversationScreen(
                messages = SampleData.conversationSample,
                navController,
                viewModel
            )
        }
    }
}
