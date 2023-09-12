package com.jvcoding.contentproviders

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import coil.compose.AsyncImage
import com.jvcoding.contentproviders.di.AppInjection.appModule
import com.jvcoding.contentproviders.screens.ImageScreen
import com.jvcoding.contentproviders.ui.theme.ContentProvidersTheme
import com.jvcoding.contentproviders.viewmodels.ImageViewModel
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
            0
        )

        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(appModule)
        }

        val imageViewModel by inject<ImageViewModel>()

        setContent {
            ContentProvidersTheme {
                ImageScreen(imageViewModel)
            }
        }
    }
}
