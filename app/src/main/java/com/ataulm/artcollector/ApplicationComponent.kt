package com.ataulm.artcollector

import com.bumptech.glide.RequestManager
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun glideRequestManager(): RequestManager

    fun harvardApiMuseumApi(): HarvardArtMuseumApi

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: ArtCollectorApplication): Builder

        fun build(): ApplicationComponent
    }
}
