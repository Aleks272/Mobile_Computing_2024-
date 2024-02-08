package com.example.composetutorial

import android.R.drawable
import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import java.io.File


@Composable
fun LoadImage(path: String) {
    // call with img path as parameter
    AsyncImage(
        model = path,
        contentDescription = null,
        placeholder = painterResource(R.drawable.download),
        contentScale = ContentScale.Crop,
        modifier = Modifier.clip(CircleShape)
    )
}

fun copyToAppStorage(context: Context, newUri: Uri): Uri {
    val input = context.contentResolver.openInputStream(newUri)
    val outputFile = context.filesDir.resolve("profilepic.jpg")
    input?.copyTo(outputFile.outputStream())
    input?.close()
    return outputFile.toUri()
}


@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)

@Composable
fun ImagePreview() {
    ComposeTutorialTheme {
        Surface() {
            LoadImage(
                path = ""
            )
        }
    }
}