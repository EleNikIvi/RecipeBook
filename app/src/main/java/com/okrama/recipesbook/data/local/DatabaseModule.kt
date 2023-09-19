package com.okrama.recipesbook.data.local

import android.content.Context
import androidx.room.Room
import com.okrama.recipesbook.data.local.recipe.RecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideChannelDao(appDatabase: RecipeBookDatabase): RecipeDao {
        return appDatabase.recipeDao
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): RecipeBookDatabase {
        return Room.databaseBuilder(
            appContext,
            RecipeBookDatabase::class.java,
            "RecipeBook"
        ).build()
    }
}