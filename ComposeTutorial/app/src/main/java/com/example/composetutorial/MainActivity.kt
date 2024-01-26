package com.example.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize())
                { val navController = rememberNavController()

                    NavHost(
                            navController = navController,
                            startDestination = "Home"
                        ) {
                            composable("Home") {
                                HomeScreen(
                                    onNavigateToConversation = {
                                        navController.navigate("conversation") {
                                            popUpTo("conversation") {
                                                inclusive = true}
                                        }
                                    }
                                )
                            }
                            composable("conversation") {
                                ConversationScreen(
                                    messages = SampleData.conversationSample,
                                    onNavigateToHome = {
                                        navController.navigate("Home") {
                                            popUpTo("Home") {
                                                inclusive = true}
                                        }
                                    }
                                )
                            }
                        }
                    }
            }
        }
    }
}
data class Message(val author: String, val text: String)


/*
@Preview
@Composable
fun PreviewConversation() {
    ComposeTutorialTheme {
        Conversation(SampleData.conversationSample)
    }
}
*/