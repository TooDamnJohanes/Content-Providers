package com.jvcoding.contentproviders.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.jvcoding.contentproviders.data.model.Image
import com.jvcoding.contentproviders.domain.repository.ImageRepository

class ImageViewModel(
    private val imageRepository: ImageRepository
): ViewModel() {
    var imageState by mutableStateOf(emptyList<Image>())
        private set

    fun getImagesFromGalleryFromYesterdayToToday() {
        imageState = imageRepository.getPicturesFromYesterdayToToday()
    }
}
