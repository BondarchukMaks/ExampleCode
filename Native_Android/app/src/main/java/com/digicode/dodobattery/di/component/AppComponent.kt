package com.digicode.dodobattery.di.component

import android.app.Application
import android.content.Context
import com.digicode.dodobattery.App
import com.digicode.dodobattery.di.module.*
import com.digicode.dodobattery.di.qualifier.AppContext
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AndroidSupportInjectionModule::class,
        FirebaseModule::class,
        ViewModelModule::class,
        ActivityModule::class,
        FragmentModule::class
    ]
)
abstract class AppComponent {

    abstract fun inject(app: App)

    @Component.Builder
    internal interface Builder {

        @BindsInstance
        fun context(@AppContext context: Context) : Builder

        @BindsInstance
        fun application(application: Application) : Builder

        fun build() : AppComponent
    }
}