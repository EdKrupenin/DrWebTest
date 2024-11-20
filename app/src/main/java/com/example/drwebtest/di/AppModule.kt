package com.example.drwebtest.di

import android.app.Application
import android.content.pm.PackageManager
import com.example.drwebtest.repository.AppRepository
import com.example.drwebtest.repository.IAppRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideSomeDependency(): SomeDependency {
        return SomeDependency()
    }

    @Provides
    fun providePackageManager(application: Application): PackageManager {
        return application.packageManager
    }

    @Provides
    @Singleton
    fun provideAppRepository(
        packageManager: PackageManager
    ): IAppRepository {
        return AppRepository(packageManager)
    }
}

class SomeDependency {
    fun sayHello(): String = "Hello from Hilt!"
}