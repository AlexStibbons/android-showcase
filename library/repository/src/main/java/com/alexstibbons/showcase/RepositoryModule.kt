package com.alexstibbons.showcase

import androidx.room.Room
import com.alexstibbons.showcase.database.FaveCache
import com.alexstibbons.showcase.database.FaveCacheImpl
import com.alexstibbons.showcase.database.FaveRepository
import com.alexstibbons.showcase.database.FaveRepositoryImpl
import com.alexstibbons.showcase.database.domain.GetFaveIds
import com.alexstibbons.showcase.database.domain.GetFaves
import com.alexstibbons.showcase.database.domain.RemoveFave
import com.alexstibbons.showcase.database.domain.SaveFave
import com.alexstibbons.showcase.datastore.DataStorePref
import com.alexstibbons.showcase.datastore.DataStorePrefImpl
import com.alexstibbons.showcase.firestore.FirestoreDb
import com.alexstibbons.showcase.firestore.FirestoreImpl
import com.alexstibbons.showcase.movieApi.MovieRepository
import com.alexstibbons.showcase.movieApi.MovieRepositoryImpl
import com.alexstibbons.showcase.tvApi.TvRepository
import com.alexstibbons.showcase.tvApi.TvRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val repositoryModule = module {

    single<DataStorePref> { DataStorePrefImpl(androidContext()) }

    single { Firebase.firestore } // db itself

    factory { Firebase.firestore.collection(USER_FAVE_COL) } // collection

    factory<FirestoreDb> { FirestoreImpl(get()) }

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

    factory { GetFaveIds(get()) }
    factory { GetFaves(get()) }
    factory { SaveFave(get()) }
    factory { RemoveFave(get()) }

}

private const val USER_FAVE_COL = "user-faves"
private const val MEDIA = "media"