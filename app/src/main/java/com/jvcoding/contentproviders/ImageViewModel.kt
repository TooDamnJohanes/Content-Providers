package com.jvcoding.contentproviders

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ImageViewModel: ViewModel() {
    var imageState by mutableStateOf(emptyList<Image>())
        private set

    fun updateImages(images: List<Image>) {
        this.imageState = images
    }
}