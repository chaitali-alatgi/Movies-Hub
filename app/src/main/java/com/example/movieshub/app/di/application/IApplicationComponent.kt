package com.example.movieshub.app.di.application

import com.example.movieshub.ui.BaseApplication
import com.example.movieshub.app.di.screen.IScreenComponent
import com.example.movieshub.app.di.screen.ScreenModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, RepositoryModule::class, DatabaseModule::class])
interface IApplicationComponent {

    fun inject(baseApplication: BaseApplication)

    fun plus(screenModule: ScreenModule): IScreenComponent
}