package com.example.composetutorial

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.composetutorial.ui.theme.ComposeTutorialTheme

@Composable
fun HomeScreen(
    onNavigateToConversation: () -> Unit
) {
    Column() {
        Text(
            text = "Contacts:",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = onNavigateToConversation) {
            Text(text = "Mörri")
        }
    }
}
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)

@Composable
fun HomePreview() {
    ComposeTutorialTheme {
        Surface() {
            val navController = rememberNavController()
            HomeScreen(
                onNavigateToConversation = { navController.navigate("conversation") }
            )
        }
    }
}