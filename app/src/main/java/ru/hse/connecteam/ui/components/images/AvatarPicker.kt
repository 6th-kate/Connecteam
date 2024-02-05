package ru.hse.connecteam.ui.components.images

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import ru.hse.connecteam.ui.components.modals.UploadImageAlertDialog
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.WhiteSemiTransparent25
import java.io.File
import java.io.FileOutputStream


@Composable
fun AvatarPicker(
    image: Bitmap? = null,
    enabled: Boolean = true,
    onImageChosen: (Bitmap?) -> Unit = {},
    onError: (Exception?) -> Unit = {}
) {
    var openImageChooser by remember { mutableStateOf(false) }

    val context = LocalContext.current
    // For gallery image chooser
    val imageCropLauncher = rememberLauncherForActivityResult(
        contract = CropImageContract()
    ) { result ->
        if (result.isSuccessful) {
            val cropped =
                BitmapFactory.decodeFile(result.getUriFilePath(context, true))
            onImageChosen(cropped)
        } else {
            onError(result.error)
        }
    }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            val cropOptions = CropImageContractOptions(uri, CropImageOptions())
            imageCropLauncher.launch(cropOptions)
        }

    // For camera image
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { cameraBitmap ->
        cameraBitmap?.let {
            val fileName = "IMG_${System.currentTimeMillis()}.jpg"
            val imageFile = File(context.filesDir, fileName)
            try {
                val out = FileOutputStream(imageFile)
                it.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
                out.close()
                onImageChosen(it)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    if (openImageChooser) {
        UploadImageAlertDialog(
            onCameraClick = {
                cameraLauncher.launch()
                openImageChooser = false
            },
            onGalleryClick = {
                imagePickerLauncher.launch("image/*")
                openImageChooser = false
            },
            onDismissClick = {
                openImageChooser = false
            }
        )
    }

    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(horizontal = 130.dp, vertical = 10.dp)
            .aspectRatio(1f)
            .background(
                color = WhiteSemiTransparent25,
                shape = CircleShape
            )
            .clickable {
                if (enabled) {
                    openImageChooser = true
                }
            }
    ) {
        if (image != null) {
            Image(
                bitmap = image.asImageBitmap(),
                contentDescription = "Аватар",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .fillMaxSize()
            )
        } else {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "Аватар не выбран",
                tint = WhiteSemiTransparent25,
                modifier = Modifier.size(70.dp)
            )
        }
    }
}

@Preview
@Composable
fun AvatarPickerPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            var image: Bitmap? by remember {
                mutableStateOf(null)
            }
            AvatarPicker(
                image,
                onImageChosen = {
                    image = it
                    Log.i("info", "added image $image")
                },
                onError = { it?.printStackTrace() })
        }
    }
}