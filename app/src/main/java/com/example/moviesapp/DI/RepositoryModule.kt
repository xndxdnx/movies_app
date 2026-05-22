package com.example.moviesapp.DI

import com.example.moviesapp.domain.repository.MovieListRepository
import com.example.moviesapp.domain.repository.MovieListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


// для оптимиз
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds // связывать интерф с реализацией без явного создания объекта
    @Singleton
    abstract fun bindsMovieListRepository (movieLitRepositoryImpl: MovieListRepositoryImpl ) : MovieListRepository
    // абстрактый класс - фундамент для наследования в будущем
}