package com.example.movieshub.app.di.application

import com.example.movieshub.BaseApplication
import com.example.movieshub.BuildConfig
import com.example.movieshub.data.network.INetworkEndpoint
import com.example.movieshub.utils.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule(private val application: BaseApplication) {

    @Provides
    @Singleton
    fun provideCache(): Cache {
        val cacheSize: Long = 15 * 1024 * 1024 // 15 MB
        return Cache(application.getCacheDir(), cacheSize)
    }

    @Provides
    @Singleton
    fun provideHttpClient(cache: Cache): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.cache(cache)
        builder.readTimeout(2, TimeUnit.MINUTES)
        builder.connectTimeout(2, TimeUnit.MINUTES)
        builder.retryOnConnectionFailure(false)
        builder.addNetworkInterceptor(object: Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request().newBuilder()
                val originalHttpUrl = chain.request().url()
                val url = originalHttpUrl.newBuilder().addQueryParameter("api_key", BuildConfig.API_KEY).build()
                request.url(url)
                val response: Response = chain.proceed(request.build())
                return response
            }
        })
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val builder = Retrofit.Builder()
        builder.baseUrl(BASE_URL)
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        builder.addConverterFactory(GsonConverterFactory.create())
        builder.client(okHttpClient)
        return builder.build()
    }

    @Provides
    @Singleton
    fun providesNetworkApi(retrofit: Retrofit): INetworkEndpoint {
        return retrofit.create(INetworkEndpoint::class.java)
    }
}