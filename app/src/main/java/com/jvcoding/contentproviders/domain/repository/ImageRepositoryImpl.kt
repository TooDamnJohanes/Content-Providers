package com.jvcoding.contentproviders.domain.repository

import com.jvcoding.contentproviders.data.model.Image
import com.jvcoding.contentproviders.domain.datasource.ImageLocalDataSource

class ImageRepositoryImpl(
    private val imageLocalDataSource: ImageLocalDataSource
): ImageRepository {
    override fun getPicturesFromYesterdayToToday(): List<Image> {
        return imageLocalDataSource.getPicturesFromYesterdayToToday()
    }
}
