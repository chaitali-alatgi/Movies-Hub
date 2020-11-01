package com.example.movieshub.ui

import android.app.Application
import com.example.movieshub.app.di.application.*
import com.example.movieshub.utils.InternetUtil

class BaseApplication: Application() {

    lateinit var component: IApplicationComponent

    override fun onCreate() {
        super.onCreate()
        inject()
        InternetUtil.init(this)
    }

    private fun inject() {
        component = DaggerIApplicationComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule(this))
            .repositoryModule(RepositoryModule())
            .databaseModule(DatabaseModule(this))
            .build()
        component.inject(this)
    }
}