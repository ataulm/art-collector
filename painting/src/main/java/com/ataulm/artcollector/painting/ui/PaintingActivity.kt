package com.ataulm.artcollector.painting.ui

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ataulm.artcollector.DataObserver
import com.ataulm.artcollector.imageUrl
import com.ataulm.artcollector.painting.R
import com.ataulm.artcollector.painting.domain.Painting
import com.ataulm.artcollector.painting.domain.PaintingId
import com.ataulm.artcollector.painting.injectDependencies
import com.ataulm.artcollector.webIntent
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_painting.*
import javax.inject.Inject

class PaintingActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModel: PaintingViewModel

    @Inject
    internal lateinit var glideRequestManager: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_painting)

        val paintingId = intent.data!!.pathSegments.last()
        injectDependencies(PaintingId(paintingId))

        // TODO: check with app links that it still works and doesn't care about postponed transitions
        postponeEnterTransition()

        intent.loadImageIfAvailable()
        viewModel.painting.observe(this, DataObserver<Painting> { painting ->
            title = painting.title
            titleArtistTextView.text = "${painting.title} // ${painting.artist.name}" // TODO: string format, but also PaintingUiModel
            creditLineTextView.text = painting.creditLine ?: "Harvard Art Museum"
            creditLineTextView.setOnClickListener { startActivity(webIntent(painting.webUrl)) }
            painting.loadImageIfDifferent()
        })
    }

    private fun Intent.loadImageIfAvailable() {
        imageUrl()?.let {
            glideRequestManager
                    .load(it)
                    .listener(startTransitionRequestListener)
                    .into(imageView)
        }
    }

    private fun Painting.loadImageIfDifferent() {
        if (imageUrl != null && intent.imageUrl() != imageUrl) {
            glideRequestManager
                    .load(imageUrl)
                    .listener(startTransitionRequestListener)
                    .into(imageView)
        }
    }

    private val startTransitionRequestListener = object : RequestListener<Drawable> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
            startPostponedEnterTransition()
            return false
        }

        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            startPostponedEnterTransition()
            return false
        }
    }
}
