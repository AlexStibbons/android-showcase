package com.alexstibbons.showcase

import android.content.Context
import org.koin.dsl.module

val openLinkModule = module {
    factory<LinkResource> { (context: Context) ->
        LinkResourceImpl(
            context
        )
    }
}