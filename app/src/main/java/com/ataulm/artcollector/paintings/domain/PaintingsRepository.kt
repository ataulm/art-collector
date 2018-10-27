package com.ataulm.artcollector.paintings.domain

import kotlinx.coroutines.Deferred

internal interface PaintingsRepository {

    fun paintings(): Deferred<Any>
}
