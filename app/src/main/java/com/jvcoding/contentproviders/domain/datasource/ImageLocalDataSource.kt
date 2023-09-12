package com.jvcoding.contentproviders.domain.datasource

import com.jvcoding.contentproviders.data.model.Image

interface ImageLocalDataSource {

    fun getPicturesFromYesterdayToToday(): List<Image>

}
