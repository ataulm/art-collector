package com.ataulm.artcollector.painting.ui

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

    private var paintingImageViewTarget: Target<Drawable>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_painting)

        val paintingId = intent.data!!.pathSegments.last()
        injectDependencies(PaintingId(paintingId))

        postponeEnterTransition()

        intent.loadImageIfAvailable()
        viewModel.painting.observe(this, DataObserver<Painting> { painting ->
            title = painting.title
            titleArtistTextView.text = getString(R.string.painting_title_artist, painting.title, painting.artist.name)
            creditLineTextView.text = painting.creditLine?.let { getString(R.string.painting_credit, it) }
                    ?: getString(R.string.painting_credit_fallback)
            creditLineTextView.setOnClickListener { startActivity(webIntent(painting.webUrl)) }
            informationContainer.visibility = View.VISIBLE
            painting.loadImageIfDifferent()
        })

        imageView.setOnClickListener {
            if (informationContainer.visibility == View.VISIBLE) {
                informationContainer.visibility = View.GONE
            } else {
                informationContainer.visibility = View.VISIBLE
            }
        }
    }

    private fun Intent.loadImageIfAvailable() {
        imageUrl()?.let {
            paintingImageViewTarget = glideRequestManager
                    .load(it)
                    .listener(startTransitionRequestListener)
                    .into(imageView)
        }
    }

    private fun Painting.loadImageIfDifferent() {
        paintingImageViewTarget?.request?.clear()
        paintingImageViewTarget = glideRequestManager
                .load(imageUrl)
                .listener(startTransitionRequestListener)
                .into(imageView)
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
