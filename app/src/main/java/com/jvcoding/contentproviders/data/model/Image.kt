package com.jvcoding.contentproviders.data.model

import android.net.Uri

data class Image(
    val id: Long,
    val displayName: String,
    val contentUri: Uri
)