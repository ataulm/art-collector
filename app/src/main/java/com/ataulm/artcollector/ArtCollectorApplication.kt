package com.ataulm.artcollector

import android.app.Application
import android.content.Context

class ArtCollectorApplication : Application() {

    private val component: ApplicationComponent by lazy {
        DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
    }

    companion object {

        fun component(context: Context) =
                (context.applicationContext as ArtCollectorApplication).component
    }
}
