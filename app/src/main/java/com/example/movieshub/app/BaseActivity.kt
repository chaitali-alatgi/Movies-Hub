package com.example.movieshub.app

import androidx.appcompat.app.AppCompatActivity
import com.example.movieshub.BaseApplication
import com.example.movieshub.app.di.application.IApplicationComponent
import com.example.movieshub.app.di.screen.ScreenModule

open class BaseActivity : AppCompatActivity() {

    val screenComponent by lazy {
        getApplicationComponent().plus(ScreenModule(this))

    }
    private fun getApplicationComponent(): IApplicationComponent {
        return (application as BaseApplication).component
    }
}
