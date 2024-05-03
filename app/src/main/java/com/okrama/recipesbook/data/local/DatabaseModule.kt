package com.okrama.recipesbook.data.local

import android.content.Context
import androidx.room.Room
import com.okrama.recipesbook.data.local.dao.CategoryAndRecipeDao
import com.okrama.recipesbook.data.local.dao.CategoryDao
import com.okrama.recipesbook.data.local.dao.RecipeDao
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
    fun provideRecipeDao(appDatabase: RecipeBookDatabase): RecipeDao {
        return appDatabase.recipeDao
    }

    @Provides
    fun provideCategoryDao(appDatabase: RecipeBookDatabase): CategoryDao {
        return appDatabase.categoryDao
    }

    @Provides
    fun provideCategoryAndRecipeDao(appDatabase: RecipeBookDatabase): CategoryAndRecipeDao {
        return appDatabase.categoryAndRecipeDao
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): RecipeBookDatabase {
        return Room.databaseBuilder(
            appContext,
            RecipeBookDatabase::class.java,
            "RecipeBook"
        ).createFromAsset("database/recipebook.db").build()
    }
}