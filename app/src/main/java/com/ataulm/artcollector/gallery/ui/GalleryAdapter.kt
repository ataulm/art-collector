package com.ataulm.artcollector.gallery.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ataulm.artcollector.R
import com.ataulm.artcollector.gallery.domain.Painting
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.itemview_painting.view.*

internal class GalleryAdapter constructor(
        private val glideRequestManager: RequestManager,
        private val onClick: (Int) -> Unit,
        private val onClickArtist: (Int) -> Unit
) : ListAdapter<Painting, GalleryAdapter.PaintingViewHolder>(PaintingDiffer) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaintingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemview_painting, parent, false)
        return PaintingViewHolder(glideRequestManager, onClick, onClickArtist, view)
    }

    override fun onBindViewHolder(viewHolder: PaintingViewHolder, position: Int) = viewHolder.bind(getItem(position))

    object PaintingDiffer : DiffUtil.ItemCallback<Painting>() {
        override fun areItemsTheSame(oldItem: Painting, newItem: Painting) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Painting, newItem: Painting) = oldItem == newItem
    }

    internal class PaintingViewHolder(
            private val glideRequestManager: RequestManager,
            private val onClick: (Int) -> Unit,
            private val onClickArtist: (Int) -> Unit,
            view: View
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: Painting) {
            itemView.setOnClickListener { onClick(adapterPosition) }
            itemView.artistTextView.text = item.artist.name
            itemView.artistTextView.setOnClickListener { onClickArtist(adapterPosition) }
            glideRequestManager
                    .load(item.imageUrl)
                    .into(itemView.imageView)
        }
    }
}
