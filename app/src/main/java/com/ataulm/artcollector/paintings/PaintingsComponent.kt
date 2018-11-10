package com.ataulm.artcollector.paintings

import com.ataulm.artcollector.ApplicationComponent
import com.ataulm.artcollector.ArtCollectorApplication
import com.ataulm.artcollector.paintings.ui.PaintingsActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [PaintingsModule::class], dependencies = [ApplicationComponent::class])
internal interface PaintingsComponent {

    fun inject(activity: PaintingsActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun activity(activity: PaintingsActivity): Builder

        fun withParent(component: ApplicationComponent): Builder

        fun build(): PaintingsComponent
    }
}

internal fun PaintingsActivity.injectDependencies() {
    DaggerPaintingsComponent.builder()
            .withParent(ArtCollectorApplication.component(this))
            .activity(this)
            .build()
            .inject(this)
}
