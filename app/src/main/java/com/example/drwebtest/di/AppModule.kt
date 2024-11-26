package com.example.drwebtest.di

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import com.example.drwebtest.repository.AppRepository
import com.example.drwebtest.repository.IAppRepository
import com.example.drwebtest.utils.AppChangeManager
import com.example.drwebtest.utils.ChecksumUtils
import com.example.drwebtest.utils.IAppChangeManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideChecksumUtils(): ChecksumUtils {
        return ChecksumUtils()
    }

    @Provides
    fun providePackageManager(application: Application): PackageManager {
        return application.packageManager
    }

    @Provides
    @Singleton
    fun provideAppChangeManager(@ApplicationContext context: Context): IAppChangeManager {
        return AppChangeManager(context)
    }

    @Provides
    @Singleton
    fun provideAppRepository(
        packageManager: PackageManager,
        checksumUtils: ChecksumUtils
    ): IAppRepository {
        return AppRepository(packageManager,checksumUtils)
    }
}