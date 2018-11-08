package com.ataulm.artcollector

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * This activity will be removed. It's necessary for the base feature to have a launcher activity.
 * This will be `PaintingsActivity`, which will be consumed by this module (`app`) again, when I add another feature module.
 */
class TemporaryHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Navigation.PAINTINGS.viewIntent())
    }
}

enum class Navigation(private val semiQualifiedActivityName: String) {

    PAINTINGS("paintings.ui.PaintingsActivity");

    fun viewIntent(): Intent {
        val componentName = "${BuildConfig.APPLICATION_ID}.$semiQualifiedActivityName"
        return Intent(Intent.ACTION_VIEW)
                .setClassName(BuildConfig.APPLICATION_ID, componentName)
    }
}
