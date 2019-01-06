package com.ataulm.artcollector.gallery.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ataulm.artcollector.R
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.itemview_painting.view.*

internal class GalleryAdapter constructor(
        private val glideRequestManager: RequestManager,
        private val onClick: (UiPainting, Int) -> Unit,
        private val onClickArtist: (UiPainting) -> Unit
) : PagedListAdapter<UiPainting, GalleryAdapter.PaintingViewHolder>(PaintingDiffer) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaintingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemview_painting, parent, false)
        return PaintingViewHolder(glideRequestManager, onClick, onClickArtist, view)
    }

    override fun onBindViewHolder(viewHolder: PaintingViewHolder, position: Int) {
        val uiPainting = getItem(position)!! // we don't support placeholders yet so these are non-null
        viewHolder.bind(uiPainting)
    }

    object PaintingDiffer : DiffUtil.ItemCallback<UiPainting>() {
        override fun areItemsTheSame(oldItem: UiPainting, newItem: UiPainting) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: UiPainting, newItem: UiPainting) = oldItem == newItem
    }

    internal class PaintingViewHolder(
            private val glideRequestManager: RequestManager,
            private val onClick: (UiPainting, Int) -> Unit,
            private val onClickArtist: (UiPainting) -> Unit,
            view: View
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: UiPainting) {
            itemView.setOnClickListener { onClick(item, adapterPosition) }
            itemView.artistTextView.text = item.artistName
            itemView.artistTextView.setOnClickListener { onClickArtist(item) }
            glideRequestManager
                    .load(item.imageUrl)
                    .into(itemView.imageView)
        }
    }
}
