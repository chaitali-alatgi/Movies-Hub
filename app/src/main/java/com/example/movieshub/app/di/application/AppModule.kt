package com.example.movieshub.app.di.application

import android.content.Context
import com.example.movieshub.ui.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val baseApplication: BaseApplication) {

    @Provides
    @Singleton
    fun provideApplication(): BaseApplication {
        return baseApplication
    }

    @Provides
    @Singleton
    fun providesContext(): Context {
        return baseApplication.getApplicationContext()
    }
}