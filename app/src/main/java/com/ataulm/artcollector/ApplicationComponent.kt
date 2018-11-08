package com.ataulm.artcollector

import android.app.Application
import com.squareup.picasso.Picasso
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: Application)

    fun picasso(): Picasso

    fun harvardApiMuseumApi(): HarvardArtMuseumApi

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: ArtCollectorApplication): Builder

        fun build(): ApplicationComponent
    }
}
