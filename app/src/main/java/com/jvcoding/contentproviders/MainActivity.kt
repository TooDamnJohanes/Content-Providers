package com.jvcoding.contentproviders

import android.Manifest
import android.content.ContentUris
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import coil.compose.AsyncImage
import com.jvcoding.contentproviders.ui.theme.ContentProvidersTheme

class MainActivity : ComponentActivity() {
    private val imageViewModel by viewModels<ImageViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
            0
        )
        val projection = arrayOf(
            MEDIA_STORE_IMAGE_ID,
            MEDIA_STORE_IMAGE_DISPLAY_NAME,
        )
        val millisYesterday = TimeUtils.getYesterdayInMillis().toString()

        val selection = "$MEDIA_STORE_IMAGE_DATE_TAKEN >= ?"
        val selectionArgs = arrayOf(millisYesterday)
        val sortOrder = "$MEDIA_STORE_IMAGE_DATE_TAKEN DESC"
        contentResolver.query(
            MEDIA_STORE_IMAGE_URI, // uri
            projection, // projection
            selection, // selection
            selectionArgs, // selectionArgs
            sortOrder // sortOrder
        )?.use { cursor ->
            val _idColum = cursor.getColumnIndex(MEDIA_STORE_IMAGE_ID)
            val _displayNameColum = cursor.getColumnIndex(MEDIA_STORE_IMAGE_DISPLAY_NAME)
            val images = mutableListOf<Image>()

            while (cursor.moveToNext()) {
                val id = cursor.getLong(_idColum)
                val displayName = cursor.getString(_displayNameColum)
                val contentUri = ContentUris.withAppendedId(
                    MEDIA_STORE_IMAGE_URI,
                    id,
                )
                images.add(Image(id, displayName, contentUri))
            }
            imageViewModel.updateImages(images)
        }
        setContent {
            ContentProvidersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(imageViewModel.imageState) { image ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AsyncImage(
                                    model = image.contentUri,
                                    contentDescription = null
                                )
                                Text(
                                    text = image.displayName,
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        fontStyle = FontStyle.Italic
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        val MEDIA_STORE_IMAGE_URI: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        const val MEDIA_STORE_IMAGE_DATE_TAKEN = MediaStore.Images.Media.DATE_TAKEN
        const val MEDIA_STORE_IMAGE_ID = MediaStore.Images.Media._ID
        const val MEDIA_STORE_IMAGE_DISPLAY_NAME = MediaStore.Images.Media.DISPLAY_NAME
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ContentProvidersTheme {
        Greeting("Android")
    }
}

data class Image(
    val id: Long,
    val displayName: String,
    val contentUri: Uri
)