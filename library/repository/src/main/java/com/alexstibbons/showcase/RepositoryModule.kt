package com.alexstibbons.showcase

import com.alexstibbons.showcase.movieApi.MovieRepository
import com.alexstibbons.showcase.movieApi.MovieRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val repositoryModule = module {

    factory<MovieRepository> {
        val apiKey = getProperty<String>("API_KEY")
        MovieRepositoryImpl(
            get<Retrofit>(named(MDB_SERVER)).movieApi(),
            apiKey
        )
    }
}