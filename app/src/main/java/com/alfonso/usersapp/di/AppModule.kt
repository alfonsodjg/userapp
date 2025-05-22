package com.alfonso.usersapp.di

import android.content.Context
import androidx.room.Room
import com.alfonso.usersapp.data.local.dao.UserDao
import com.alfonso.usersapp.data.local.database.UserDataBase
import com.alfonso.usersapp.data.modules.users.repository.ImplUsersRepository
import com.alfonso.usersapp.data.network.NetworkConnectivity
import com.alfonso.usersapp.data.network.endpoint.IRetrofit
import com.alfonso.usersapp.domain.conection.ConnectivityObserver
import com.alfonso.usersapp.domain.modules.users.repository.IUsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //Network provider
    @Provides
    @Singleton
    fun provideConnectivityObserver(
        @ApplicationContext context: Context
    ): ConnectivityObserver = NetworkConnectivity(context)

    @Provides
    fun provideBaseUrl(): String = "https://jsonplaceholder.typicode.com/"

    //Retrofit provider
    @Singleton
    @Provides
    fun provideRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): IRetrofit =
        retrofit.create(IRetrofit::class.java)

    //Room provides
    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context): UserDataBase =
        Room.databaseBuilder(context, UserDataBase::class.java, "app_database")
            .fallbackToDestructiveMigration(true)
            .build()

    @Provides
    @Singleton
    fun provideUserDao(db: UserDataBase): UserDao = db.userDao()

    //Repository provider
    @Singleton
    @Provides
    fun provideUsersRepository(api: IRetrofit, cached: UserDao): IUsersRepository =
        ImplUsersRepository(api, cached)

}