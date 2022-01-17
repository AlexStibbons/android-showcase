package com.alexstibbons.showcase

import com.alexstibbons.showcase.movieApi.MovieApi
import com.alexstibbons.showcase.network.NetworkHandler
import com.alexstibbons.showcase.network.NetworkHandlerImpl
import com.alexstibbons.showcase.tvApi.TvApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val READ_TIMEOUT = 20L
const val CONNECT_TIMEOUT = 20L

val networkModule = module(override = true) {
    factory<Converter.Factory> { GsonConverterFactory.create(get()) }
    factory<NetworkHandler> {
        NetworkHandlerImpl(
            androidApplication()
        )
    }
    factory<Gson> {
        GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .setLenient()
            .create()
    }
    factory { httpLoggingDebugInterceptor }

    single(named(MDB_SERVER)) { retrofit(movieDbServerUrl) }
}

private val httpLoggingDebugInterceptor: HttpLoggingInterceptor
    get() {
        return HttpLoggingInterceptor()
            .apply { level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE }
    }

private fun Scope.retrofit(serverUrl: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(serverUrl)
        .addConverterFactory(get())
        .client(okHttpClient(serverUrl))
        .build()
}

private fun Scope.okHttpClient(serverUrl: String): OkHttpClient {

    return OkHttpClient.Builder()
        .addInterceptor(get<HttpLoggingInterceptor>())
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .build()
}

const val MDB_SERVER = "movieDbServer"
val Scope.movieDbServerUrl: String get() = getProperty("MOVIE_SERVER_URL")

fun Retrofit.movieApi(): MovieApi = this.create(MovieApi::class.java)
fun Retrofit.tvApi(): TvApi = this.create(TvApi::class.java)