package com.example.thenest_maintrial.di

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.thenest_maintrial.data.local.AppDatabase
import com.example.thenest_maintrial.data.remote.ApiService
import com.example.thenest_maintrial.repo.userRepository.UserRepository
import com.example.thenest_maintrial.repo.userRepository.UserRepositoryImpl
import com.example.thenest_maintrial.utils.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://the-nest-20b4c6214fc5.herokuapp.com/api/v1/" //todo check if need change to https

    private fun getOkHttpClient(context: Context):OkHttpClient.Builder =
        try {
            val loggingInterceptor = HttpLoggingInterceptor {message ->
                Log.i("OkHttp", message)
            }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
            val builder = OkHttpClient.Builder()
            builder.addInterceptor(loggingInterceptor)
            builder.addInterceptor{
                AuthInterceptor(context).intercept(it)
            }
            builder
        }catch (e:Exception){
            throw RuntimeException(e)
        }

    @Provides
    @Singleton
    fun provideToDoDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "app_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserRepository(database: AppDatabase): UserRepository {

        return UserRepositoryImpl(database.getUserDao())
    }

    @Provides
    @Singleton
    fun provideRetrofitCall(@ApplicationContext context: Context): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient(context).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(retrofit: Retrofit): ApiService {

        return retrofit.create(ApiService::class.java)
    }

}
