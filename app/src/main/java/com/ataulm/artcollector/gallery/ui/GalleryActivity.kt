package com.ataulm.artcollector.gallery.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.ataulm.artcollector.DataObserver
import com.ataulm.artcollector.EventObserver
import com.ataulm.artcollector.Navigation
import com.ataulm.artcollector.R
import com.ataulm.artcollector.gallery.domain.Gallery
import com.ataulm.artcollector.gallery.injectDependencies
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_gallery.*
import javax.inject.Inject

class GalleryActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModel: PaintingsViewModel

    @Inject
    internal lateinit var picasso: Picasso

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        val galleryAdapter = GalleryAdapter(picasso) { viewModel.onClick(it) }
        recyclerView.apply {
            adapter = galleryAdapter
            layoutManager = GridLayoutManager(this@GalleryActivity, 2)
        }

        viewModel.gallery.observe(this, DataObserver<Gallery> { gallery ->
            galleryAdapter.submitList(gallery)
        })

        viewModel.events.observe(this, EventObserver {
            val painting = it.painting
            startActivity(Navigation.PAINTING.viewIntent(painting.artist.id, painting.id))
        })
    }
}
