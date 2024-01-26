package com.example.composetutorial

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.Navigation.findNavController
import androidx.navigation.compose.rememberNavController
import com.example.composetutorial.ui.theme.ComposeTutorialTheme


@Composable
fun ConversationScreen(
    messages: List<Message>,
    onNavigateToHome: () -> Unit,
) {
    Column() {

        Surface(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .size(75.dp)
                .padding(1.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
        ) {
            Button(onClick = onNavigateToHome) {
                Image(
                    painter = painterResource(R.drawable._23483_200),
                    contentDescription = "Home icon",
                    modifier = Modifier
                        .scale(2F)
                )
            }
        }
        Spacer(
            modifier = Modifier
                .height(4.dp)
        )

        LazyColumn {
            items(messages) { message ->
                MyMessage(message)
            }
        }
    }
}
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)

@Composable
fun ConversationPreview() {
    ComposeTutorialTheme {
        Surface() {
            val navController = rememberNavController()

            ConversationScreen(
                messages = SampleData.conversationSample,
                onNavigateToHome = { navController.navigate("Home") }
            )
        }
    }
}
