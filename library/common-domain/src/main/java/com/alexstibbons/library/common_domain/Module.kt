package com.alexstibbons.library.common_domain

import org.koin.dsl.module

val commonDomainModule = module {

    factory { GetUserId(get()) }
    factory { SaveUserId(get()) }
}