package com.erdemklync.shopin.di

import com.erdemklync.shopin.data.remote.repository.CartRepositoryImpl
import com.erdemklync.shopin.data.remote.repository.CategoryRepositoryImpl
import com.erdemklync.shopin.data.remote.repository.ProductRepositoryImpl
import com.erdemklync.shopin.domain.repository.CartRepository
import com.erdemklync.shopin.domain.repository.CategoryRepository
import com.erdemklync.shopin.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun provideProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository

    @Binds
    @Singleton
    abstract fun provideCartRepository(cartRepositoryImpl: CartRepositoryImpl): CartRepository

    @Binds
    @Singleton
    abstract fun provideCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository
}