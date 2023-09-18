package com.okrama.recipesbook.data

import com.okrama.recipesbook.domain.recipe.RecipeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    @ViewModelScoped
    fun bindRecipeRepository(repository: RecipeRepositoryImpl): RecipeRepository
}