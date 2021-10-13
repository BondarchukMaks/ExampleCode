package com.digicode.dodobattery.di.module

import android.content.Context
import com.digicode.dodobattery.Logger
import com.digicode.dodobattery.Purchaser
import com.digicode.dodobattery.data.backend.AppService
import com.digicode.dodobattery.data.preferences.PreferencesFacade
import com.digicode.dodobattery.di.qualifier.AppContext
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providePreferencesFacade(
        @AppContext
        context: Context
    ) : PreferencesFacade {
        return PreferencesFacade(context)
    }

    @Provides
    @Singleton
    fun provideLogger(
        @AppContext
        context: Context
    ) : Logger {
        return Logger(context)
    }

    @Provides
    @Singleton
    fun providePurchaser(
    ) : Purchaser{
        return Purchaser()
    }


    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fcm.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideAppService(retrofit: Retrofit): AppService {
        return retrofit.create(AppService::class.java)
    }


}