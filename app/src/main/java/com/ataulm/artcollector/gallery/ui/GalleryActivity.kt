package com.ataulm.artcollector.gallery.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ataulm.artcollector.DataObserver
import com.ataulm.artcollector.EventObserver
import com.ataulm.artcollector.R
import com.ataulm.artcollector.artistGalleryIntent
import com.ataulm.artcollector.gallery.domain.Gallery
import com.ataulm.artcollector.gallery.injectDependencies
import com.ataulm.artcollector.paintingIntent
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.itemview_painting.view.*
import javax.inject.Inject

class GalleryActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModel: GalleryViewModel

    @Inject
    internal lateinit var glideRequestManager: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        val adapter = GalleryAdapter(
                glideRequestManager,
                onClickPainting,
                onClickArtist
        )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this@GalleryActivity, 2)

        viewModel.gallery.observe(this, DataObserver<Gallery> { gallery ->
            adapter.submitList(gallery)
        })

        viewModel.events.observe(this, EventObserver { command ->
            when (command) {
                is NavigateToArtistGallery -> navigateToArtistGallery(command)
                is NavigateToPainting -> navigateToPainting(command)
            }
        })
    }

    private val onClickArtist: (Int) -> Unit = { viewModel.onClickArtist(it) }

    private val onClickPainting: (Int) -> Unit = { viewModel.onClick(it) }

    private fun navigateToArtistGallery(it: NavigateToArtistGallery) {
        val intent = artistGalleryIntent(it.artist.id)
        startActivity(intent)
    }

    private fun navigateToPainting(command: NavigateToPainting) {
        val (painting, adapterPosition) = command.painting to command.adapterPosition
        val paintingIntent = paintingIntent(painting.artist.id, painting.id, painting.imageUrl)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, recyclerView.sharedElements(adapterPosition))
        startActivity(paintingIntent, options.toBundle())
    }

    private fun RecyclerView.sharedElements(adapterPosition: Int): Pair<View, String> {
        val itemView = layoutManager?.findViewByPosition(adapterPosition)!!
        return Pair(itemView.imageView as View, getString(R.string.shared_element_painting))
    }
}
