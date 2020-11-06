package com.alexstibbons.showcase

import androidx.room.Room
import com.alexstibbons.showcase.database.FaveCache
import com.alexstibbons.showcase.database.FaveCacheImpl
import com.alexstibbons.showcase.database.FaveRepository
import com.alexstibbons.showcase.database.FaveRepositoryImpl
import com.alexstibbons.showcase.movieApi.MovieRepository
import com.alexstibbons.showcase.movieApi.MovieRepositoryImpl
import com.alexstibbons.showcase.tvApi.TvRepository
import com.alexstibbons.showcase.tvApi.TvRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val repositoryModule = module {

    factory<MovieRepository> {
        MovieRepositoryImpl(
            get<Retrofit>(named(MDB_SERVER)).movieApi()
        )
    }

    factory<TvRepository> {
        TvRepositoryImpl(
            get<Retrofit>(named(MDB_SERVER)).tvApi()
        )
    }

    factory<FaveRepository> { FaveRepositoryImpl(get()) }

    single<FaveCache> { Room.databaseBuilder(get(), FaveCacheImpl::class.java, "fave_database").build() }
    single { get<FaveCacheImpl>().faveDao() }
}