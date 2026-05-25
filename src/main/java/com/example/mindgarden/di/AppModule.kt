package com.example.mindgarden.di

import android.content.Context
import androidx.room.Room
import com.example.mindgarden.data.local.AppDatabase
import com.example.mindgarden.data.local.FlowerDao
import com.example.mindgarden.data.repository.FlowerRepositoryImpl
import com.example.mindgarden.domain.repository.FlowerRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext ctx: Context): AppDatabase =
        Room.databaseBuilder(ctx, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideFlowerDao(db: AppDatabase): FlowerDao = db.flowerDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFlowerRepository(impl: FlowerRepositoryImpl): FlowerRepository
}
