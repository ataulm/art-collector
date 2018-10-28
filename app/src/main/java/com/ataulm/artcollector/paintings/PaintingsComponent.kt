package com.ataulm.artcollector.paintings

import com.ataulm.artcollector.paintings.ui.PaintingsActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [PaintingsModule::class])
internal interface PaintingsComponent {

    fun inject(activity: PaintingsActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun activity(activity: PaintingsActivity): Builder

        fun build(): PaintingsComponent
    }
}

internal fun PaintingsActivity.injectDependencies() {
    DaggerPaintingsComponent.builder()
            .activity(this)
            .build()
            .inject(this)
}
