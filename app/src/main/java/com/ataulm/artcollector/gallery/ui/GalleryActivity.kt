package com.ataulm.artcollector.gallery.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.ataulm.artcollector.*
import com.ataulm.artcollector.gallery.injectDependencies
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.itemview_painting.view.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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

//        val adapter = GalleryAdapter(glideRequestManager)
        val adapter = PagedGalleryAdapter(glideRequestManager)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(GallerySpacingItemDecoration(resources))
        lifecycleScope.launch {
            viewModel.pagedGallery().collectLatest { adapter.submitData(it) }
        }

//        viewModel.gallery.observe(this, DataObserver<UiGallery> { gallery ->
//            adapter.submitList(gallery)
//        })

        viewModel.events.observe(this, EventObserver { command ->
            when (command) {
                is NavigateToArtistGallery -> navigateToArtistGallery(command)
                is NavigateToPainting -> navigateToPainting(command)
            }
        })
    }

    private fun navigateToArtistGallery(it: NavigateToArtistGallery) {
        val intent = artistGalleryIntent(it.artistId)
        startActivity(intent)
    }

    private fun navigateToPainting(command: NavigateToPainting) {
        val paintingIntent = paintingIntent(command.artistId, command.paintingId, command.imageUrl)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair(command.view, getString(R.string.shared_element_painting)))
        startActivity(paintingIntent, options.toBundle())
    }
}

private class PagedGalleryAdapter(private val glideRequestManager: RequestManager)
    : PagingDataAdapter<UiPainting, PagedGalleryAdapter.PaintingViewHolder>(PaintingDiffer) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaintingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemview_painting, parent, false)
        return PaintingViewHolder(glideRequestManager, view)
    }

    override fun onBindViewHolder(viewHolder: PaintingViewHolder, position: Int) {
        // the item might be null while loading - TODO: placeholders
        getItem(position)?.let { viewHolder.bind(it) }
    }

    object PaintingDiffer : DiffUtil.ItemCallback<UiPainting>() {
        override fun areItemsTheSame(oldItem: UiPainting, newItem: UiPainting) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: UiPainting, newItem: UiPainting) = oldItem == newItem
    }

    internal class PaintingViewHolder(
            private val glideRequestManager: RequestManager,
            view: View
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: UiPainting) {
            itemView.setOnClickListener { item.onClickPainting(itemView.imageView) }
            itemView.artistTextView.text = item.artistName
            itemView.artistTextView.setOnClickListener { item.onClickArtist() }
            glideRequestManager
                    .load(item.imageUrl)
                    .into(itemView.imageView)
        }
    }
}
