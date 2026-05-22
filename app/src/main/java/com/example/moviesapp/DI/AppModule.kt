package com.example.moviesapp.DI

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviesapp.data.local.MovieDatabase
import com.example.moviesapp.data.remote.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesMovieDatabase(
        app: Application,
    ) : MovieDatabase {
        return Room.databaseBuilder(
            name = "movie.db",
            klass = MovieDatabase::class.java,
            context = app
            ).build()
    }

//////////////////////////////////////////////

    // Retrofit сlient
    // KeyWords:
    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        .apply { level = HttpLoggingInterceptor.Level.BODY }      // Это вспомогат. элемент. перехватчик Http выводить заголоки  и тело запросов и ответов

    private val client: OkHttpClient = OkHttpClient.Builder()     // Конфигуратор нашего клинта
        .addInterceptor(interceptor = interceptor)                  // создаёт фин. экземпляр http клиента
        .build()

    @Provides
    @Singleton
    fun provideMovieApi() : MovieApi {
        return Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )     // конвертер из json в kotlin
            .baseUrl(MovieApi.BASE_URL)
            .client(client)
            .build()
            .create(MovieApi::class.java)   // Генерируем реализ MovieApi на основании анотации нашего интерфейса

    }

}