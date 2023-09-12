package com.jvcoding.contentproviders.domain.utils

import java.util.Calendar

object TimeUtils {

    fun getYesterdayInMillis(): Long {
        return Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
        }.timeInMillis
    }

}