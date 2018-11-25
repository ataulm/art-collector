package com.ataulm.artcollector.artist.ui

import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.ataulm.artcollector.DataObserver
import com.ataulm.artcollector.EventObserver
import com.ataulm.artcollector.artist.R
import com.ataulm.artcollector.artist.domain.ArtistId
import com.ataulm.artcollector.artist.injectDependencies
import com.ataulm.artcollector.paintingIntent
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.activity_artist.*
import kotlinx.android.synthetic.main.itemview_artist_painting.view.*
import javax.inject.Inject

class ArtistActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModel: ArtistViewModel

    @Inject
    internal lateinit var glideRequestManager: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist)

        val artistId = intent.data!!.pathSegments.last()
        injectDependencies(ArtistId(artistId))

        val adapter = ArtistAdapter(glideRequestManager) { viewModel.onClick(it) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this@ArtistActivity, 2)

        viewModel.artistGallery.observe(this, DataObserver<ArtistGallery> { artistGallery ->
            title = artistGallery.artist.name
            adapter.submitList(artistGallery.gallery)
        })

        viewModel.events.observe(this, EventObserver { command ->
            val (painting, adapterPosition) = command.painting to command.adapterPosition
            val paintingIntent = paintingIntent(painting.artist.id, painting.id, painting.imageUrl)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, recyclerView.sharedElements(adapterPosition))
            startActivity(paintingIntent, options.toBundle())
        })
    }

    private fun RecyclerView.sharedElements(adapterPosition: Int): Pair<View, String> {
        val itemView = layoutManager?.findViewByPosition(adapterPosition)!!
        return Pair(itemView.imageView as View, getString(com.ataulm.artcollector.R.string.shared_element_painting))
    }
}
