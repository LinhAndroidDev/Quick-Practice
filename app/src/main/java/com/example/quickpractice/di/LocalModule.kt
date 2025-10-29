package com.example.quickpractice.di

import com.example.quickpractice.util.SharePreferenceRepository
import com.example.quickpractice.util.SharePreferenceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalModule {

    @Binds
    @Singleton
    abstract fun bindSharePreferenceRepository(
        impl: SharePreferenceRepositoryImpl
    ): SharePreferenceRepository
}