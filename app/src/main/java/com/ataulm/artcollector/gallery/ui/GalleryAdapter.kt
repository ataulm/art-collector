package com.ataulm.artcollector.gallery.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ataulm.artcollector.R
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.itemview_painting.view.*

internal class GalleryAdapter(private val glideRequestManager: RequestManager)
    : PagingDataAdapter<UiPainting, GalleryAdapter.PaintingViewHolder>(PaintingDiffer) {

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
