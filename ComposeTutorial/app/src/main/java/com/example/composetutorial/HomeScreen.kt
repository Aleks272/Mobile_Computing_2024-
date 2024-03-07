package com.example.composetutorial

import android.app.NotificationManager
import android.content.res.Configuration
import android.graphics.drawable.Icon
import android.net.Uri
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.SensorManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.composetutorial.ui.theme.ComposeTutorialTheme


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: UserViewModel
) {
    val currentUser by viewModel.currentUser.observeAsState()

    val context = LocalContext.current

    val notification = Notification()

    var pressureValue = viewModel.lightData.observeAsState()

    viewModel.insertDefaultUser("default_user", "uri")

    var username by remember {
        mutableStateOf(viewModel.findUserById(0).username ?: "default_user")
    }

    var chosenImg by remember {
        mutableStateOf(viewModel.findUserByName(username).image)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri ->
            if (uri != null) {
                viewModel.saveUser(username, copyToAppStorage(context,uri).toString())
            }
                chosenImg = uri.toString()
        }
    )

    var hasNotificationPermission by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
        } else {
            mutableStateOf(true)
        }
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasNotificationPermission = isGranted
            }
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row {
            Button(onClick = {
                navController.navigate("Conversation") {
                    popUpTo("Conversation") {
                        inclusive = true
                    }
                }
            }
            )
            {
                Text(text = "Mörri")
            }
        }

        Text(text = "Type username here:")

        Row() {
            TextField(value = username, onValueChange = {
                username = it
            })
            IconButton(
                onClick = {
                    if (chosenImg != null) {
                        viewModel.saveUser(
                            username,
                            copyToAppStorage(context, newUri = chosenImg!!.toUri()).toString()
                        )
                    }
                }
            ) {
                Image(
                    painter = painterResource(id = android.R.drawable.ic_menu_save),
                    contentDescription = "Save name",
                    modifier = Modifier
                        .scale(0.9F)
                )
            }
        }
        Text(text = "Current username: ")

        Text(text = currentUser?.username.toString())

        Button(onClick = {
            launcher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }) {
            Text(text = "Upload profile picture")
        }

        Text(text = "Current picture: ")
        Row(){
            Image(
                painter = rememberAsyncImagePainter(chosenImg?.toUri()),
                contentDescription = "Profile picture"
            )

            IconButton(
                onClick = {
                    if (chosenImg != null) {
                        viewModel.saveUser(
                            username,
                            copyToAppStorage(context, newUri = chosenImg!!.toUri()).toString()
                        )
                    }
                }
            ) {
                Image(
                    painter = painterResource(id = android.R.drawable.ic_menu_save),
                    contentDescription = "Save picture",
                    modifier = Modifier
                        .scale(0.9F)
                )
            }
        }
        Button(onClick = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }

        }) {
            Text(text = "Request notification permission")
        }

        Button(onClick = {
            if(hasNotificationPermission) {
                notification.createNotification(context, "1")
            }
        }) {
            Text(text = "Show notification")
        }
        Button(onClick = {
            navController.navigate("Camera") {
                popUpTo("Camera") {
                    inclusive = true
                }
            }
        }) {
            Text(text = "Go to camera screen")
        }



        Text(text = "Pressure : " + pressureValue.value)
    }
}


@Composable
fun PreviewHomeScreen(
) {
    var username = "testUser"
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row{
            Button(onClick = {}
            )
            {
                Text(text = "Mörri")
            }
        }

        Text(text = "Type username here:")

        TextField(value = username, onValueChange = {
            username = it
        } )
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
            PreviewHomeScreen(
            )
        }
    }
}

