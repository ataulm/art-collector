package com.ataulm.artcollector.painting

import com.ataulm.artcollector.ApplicationComponent
import com.ataulm.artcollector.ArtCollectorApplication
import com.ataulm.artcollector.painting.domain.PaintingId
import com.ataulm.artcollector.painting.ui.PaintingActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [PaintingModule::class], dependencies = [ApplicationComponent::class])
internal interface PaintingComponent {

    fun inject(activity: PaintingActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun activity(activity: PaintingActivity): Builder

        fun withParent(component: ApplicationComponent): Builder

        @BindsInstance
        fun with(paintingId: PaintingId): Builder

        fun build(): PaintingComponent
    }
}

internal fun PaintingActivity.injectDependencies(paintingId: PaintingId) {
    DaggerPaintingComponent.builder()
            .withParent(ArtCollectorApplication.component(this))
            .with(paintingId)
            .activity(this)
            .build()
            .inject(this)
}
