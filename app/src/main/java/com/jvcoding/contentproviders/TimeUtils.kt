package com.jvcoding.contentproviders

import java.util.Calendar

object TimeUtils {

    fun getYesterdayInMillis(): Long {
        return Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
        }.timeInMillis
    }

}