package com.ataulm.artcollector.artist

import com.ataulm.artcollector.ApplicationComponent
import com.ataulm.artcollector.ArtCollectorApplication
import com.ataulm.artcollector.artist.domain.ArtistId
import com.ataulm.artcollector.artist.ui.ArtistActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ArtistModule::class], dependencies = [ApplicationComponent::class])
internal interface ArtistComponent {

    fun inject(activity: ArtistActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun activity(activity: ArtistActivity): Builder

        fun withParent(component: ApplicationComponent): Builder

        @BindsInstance
        fun with(artistId: ArtistId): Builder

        fun build(): ArtistComponent
    }
}

internal fun ArtistActivity.injectDependencies(artistId: ArtistId) = DaggerArtistComponent.builder()
        .withParent(ArtCollectorApplication.component(this))
        .activity(this)
        .with(artistId)
        .build()
        .inject(this)
