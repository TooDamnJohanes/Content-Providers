package com.jvcoding.contentproviders.domain.datasource

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import com.jvcoding.contentproviders.data.model.Image
import com.jvcoding.contentproviders.domain.utils.TimeUtils

class ImageLocalDataSourceImpl(
    private val contentResolver: ContentResolver
): ImageLocalDataSource {
    override fun getPicturesFromYesterdayToToday(): List<Image> {
        val images = mutableListOf<Image>()
        val projection = arrayOf(
            MEDIA_STORE_IMAGE_ID,
            MEDIA_STORE_IMAGE_DISPLAY_NAME,
        )
        val millisYesterday = TimeUtils.getYesterdayInMillis().toString()
        val selectionArgs = arrayOf(millisYesterday)

        contentResolver.query(
            MEDIA_STORE_IMAGE_URI, // uri
            projection, // projection
            MEDIA_STORE_IMAGE_SELECTION, // selection
            selectionArgs, // selectionArgs
            MEDIA_STORE_IMAGE_SORT_ORDER // sortOrder
        )?.use { cursor ->
            val _idColum = cursor.getColumnIndex(MEDIA_STORE_IMAGE_ID)
            val _displayNameColum = cursor.getColumnIndex(MEDIA_STORE_IMAGE_DISPLAY_NAME)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(_idColum)
                val displayName = cursor.getString(_displayNameColum)
                val contentUri = ContentUris.withAppendedId(
                    MEDIA_STORE_IMAGE_URI,
                    id,
                )
                images.add(Image(id, displayName, contentUri))
            }
        }
        return images
    }

    companion object {
        val MEDIA_STORE_IMAGE_URI: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        const val MEDIA_STORE_IMAGE_DATE_TAKEN = MediaStore.Images.Media.DATE_TAKEN
        const val MEDIA_STORE_IMAGE_ID = MediaStore.Images.Media._ID
        const val MEDIA_STORE_IMAGE_DISPLAY_NAME = MediaStore.Images.Media.DISPLAY_NAME
        const val MEDIA_STORE_IMAGE_SELECTION = "${MEDIA_STORE_IMAGE_DATE_TAKEN} >= ?"
        const val MEDIA_STORE_IMAGE_SORT_ORDER = "${MEDIA_STORE_IMAGE_DATE_TAKEN} DESC"
    }
}
