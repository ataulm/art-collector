package com.ataulm.artcollector.painting.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ataulm.artcollector.DataObserver
import com.ataulm.artcollector.painting.R
import com.ataulm.artcollector.painting.domain.Painting
import com.ataulm.artcollector.painting.domain.PaintingId
import com.ataulm.artcollector.painting.injectDependencies
import com.ataulm.artcollector.webIntent
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_painting.*
import javax.inject.Inject

class PaintingActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModel: PaintingViewModel

    @Inject
    internal lateinit var picasso: Picasso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_painting)

        val paintingId = intent.data!!.pathSegments.last()
        injectDependencies(PaintingId(paintingId))

        viewModel.painting.observe(this, DataObserver<Painting> { painting ->
            title = painting.title
            titleArtistTextView.text = "${painting.title} // ${painting.artist.name}" // TODO: string format, but also PaintingUiModel
            creditLineTextView.text = painting.creditLine ?: "Harvard Art Museum"
            creditLineTextView.setOnClickListener { startActivity(webIntent(painting.webUrl)) }
            picasso.load(painting.imageUrl).into(imageView)
        })
    }
}
