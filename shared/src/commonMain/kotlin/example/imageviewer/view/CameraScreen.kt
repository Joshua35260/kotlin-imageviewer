package example.imageviewer.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import example.imageviewer.LocalImageProvider
import example.imageviewer.model.WildstagramService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CameraScreen(onBack: (resetSelectedPicture: Boolean) -> Unit) {
    val imageProvider = LocalImageProvider.current
    var showCamera by remember { mutableStateOf(false) }
    LaunchedEffect(onBack) {
        if (!showCamera) {
            delay(300) // for animation
            showCamera = true
        }
    }
    val coroutineScope = rememberCoroutineScope()

    Box(Modifier.fillMaxSize().background(Color.Black)) {
        if (showCamera) {
            CameraView(Modifier.fillMaxSize(), onCapture = { picture, image ->
                imageProvider.saveImage(picture, image)
                coroutineScope.launch {
                    val wildstagramService = WildstagramService()
                    wildstagramService.pushImage(image)
                }
                onBack(true)
            })

            TopLayout(
                alignLeftContent = {
                    BackButton {
                        onBack(false)
                    }
                },
                alignRightContent = {},
            )
        }
    }
}
