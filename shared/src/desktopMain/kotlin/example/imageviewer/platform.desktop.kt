package example.imageviewer

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers

import java.util.UUID

actual fun Modifier.notchPadding(): Modifier = Modifier.padding(top = 12.dp)


actual fun createUUID(): String = UUID.randomUUID().toString()

actual val ioDispatcher = Dispatchers.IO

actual val isShareFeatureSupported: Boolean = false

actual val shareIcon: ImageVector = Icons.Filled.Share

actual fun ImageBitmap.toByteArray(): ByteArray {
    val r = this.asSkiaBitmap().readPixels()
    return r ?: ByteArray(0)
}


