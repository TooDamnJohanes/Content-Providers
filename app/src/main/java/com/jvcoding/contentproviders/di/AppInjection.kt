package com.jvcoding.contentproviders.di

import android.content.ContentResolver
import com.jvcoding.contentproviders.domain.datasource.ImageLocalDataSource
import com.jvcoding.contentproviders.domain.datasource.ImageLocalDataSourceImpl
import com.jvcoding.contentproviders.domain.repository.ImageRepository
import com.jvcoding.contentproviders.domain.repository.ImageRepositoryImpl
import com.jvcoding.contentproviders.viewmodels.ImageViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object AppInjection {

    val appModule = module {
        single<ContentResolver> { androidContext().contentResolver }
        single<ImageLocalDataSource> { ImageLocalDataSourceImpl(get()) }
        single<ImageRepository> { ImageRepositoryImpl(get()) }
        single<ImageViewModel> { ImageViewModel(get()) }
    }

}