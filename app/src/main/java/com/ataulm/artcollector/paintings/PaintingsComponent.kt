package com.ataulm.artcollector.paintings

import com.ataulm.artcollector.paintings.ui.PaintingsActivity
import dagger.Component

@Component(modules = [PaintingsModule::class])
internal interface PaintingsComponent {

    fun inject(activity: PaintingsActivity)

    @Component.Builder
    interface Builder {

        fun paintingsModule(module: PaintingsModule): Builder

        fun build(): PaintingsComponent
    }
}

internal fun PaintingsActivity.injectDependencies() {
    DaggerPaintingsComponent.builder()
            .build()
            .inject(this)
}
