package com.jvcoding.contentproviders.domain.repository

import com.jvcoding.contentproviders.data.model.Image

interface ImageRepository {

    fun getPicturesFromYesterdayToToday(): List<Image>

}
