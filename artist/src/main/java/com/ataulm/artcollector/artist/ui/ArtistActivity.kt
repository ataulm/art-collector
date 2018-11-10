package com.ataulm.artcollector.artist.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.ataulm.artcollector.DataObserver
import com.ataulm.artcollector.EventObserver
import com.ataulm.artcollector.artist.R
import com.ataulm.artcollector.artist.domain.ArtistId
import com.ataulm.artcollector.artist.domain.Gallery
import com.ataulm.artcollector.artist.injectDependencies
import com.ataulm.artcollector.paintingIntent
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_artist.*
import javax.inject.Inject

class ArtistActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModel: ArtistViewModel

    @Inject
    internal lateinit var picasso: Picasso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist)

        val artistId = intent.data!!.pathSegments.last()
        injectDependencies(ArtistId(artistId))

        val adapter = ArtistAdapter(picasso) { viewModel.onClick(it) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this@ArtistActivity, 2)

        viewModel.gallery.observe(this, DataObserver<Gallery> { gallery ->
            adapter.submitList(gallery)
        })

        viewModel.events.observe(this, EventObserver {
            val painting = it.painting
            startActivity(paintingIntent(painting.artist.id, painting.id))
        })
    }
}
