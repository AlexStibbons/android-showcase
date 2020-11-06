package com.alexstibbons.showcase

import androidx.room.Room
import androidx.room.RoomDatabase
import com.alexstibbons.showcase.database.FaveDatabase
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

    single<RoomDatabase> { Room.databaseBuilder(get(), FaveDatabase::class.java, "fave_database").build() }
    single { get<FaveDatabase>().faveDao() }
}