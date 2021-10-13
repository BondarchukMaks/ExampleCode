package com.digicode.dodobattery.di.module

import com.digicode.dodobattery.data.backend.AppService
import com.digicode.dodobattery.data.preferences.PreferencesFacade
import com.digicode.dodobattery.firebase.config.ConfigProvider
import com.digicode.dodobattery.firebase.firestore.FirestoreProvider
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class FirebaseModule {

    @Provides
    @Singleton
    fun provideFirestore(preferencesFacade: PreferencesFacade) : FirestoreProvider {
        return FirestoreProvider(preferencesFacade)
    }

    @Provides
    @Singleton
    fun provideRemoteConfig() : ConfigProvider {
        return ConfigProvider()
    }

}