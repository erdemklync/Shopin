package com.erdemklync.shopin.di

import android.content.Context
import com.erdemklync.shopin.data.local.DataStoreManager
import com.erdemklync.shopin.data.mapper.ProductMapper
import com.erdemklync.shopin.data.mapper.RatingMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Singleton
    @Provides
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager =
        DataStoreManager(context)

    @Singleton
    @Provides
    fun provideRatingMapper(): RatingMapper = RatingMapper()

    @Singleton
    @Provides
    fun provideProductMapper(): ProductMapper = ProductMapper(provideRatingMapper())

}