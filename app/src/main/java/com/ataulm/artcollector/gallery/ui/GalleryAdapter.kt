package com.ataulm.artcollector.gallery.ui

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ataulm.artcollector.R
import com.ataulm.artcollector.gallery.domain.Painting
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.itemview_painting.view.*

internal class GalleryAdapter constructor(
        private val picasso: Picasso,
        private val onClick: (Painting) -> Unit
) : ListAdapter<Painting, GalleryAdapter.PaintingViewHolder>(PaintingDiffer) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaintingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemview_painting, parent, false)
        return PaintingViewHolder(picasso, onClick, view)
    }

    override fun onBindViewHolder(viewHolder: PaintingViewHolder, position: Int) = viewHolder.bind(getItem(position))

    object PaintingDiffer : DiffUtil.ItemCallback<Painting>() {
        override fun areItemsTheSame(oldItem: Painting, newItem: Painting) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Painting, newItem: Painting) = oldItem == newItem
    }

    internal class PaintingViewHolder(
            private val picasso: Picasso,
            private val onClick: (Painting) -> Unit,
            view: View
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: Painting) {
            itemView.setOnClickListener { onClick(item) }
            itemView.artistTextView.text = item.artist.name
            picasso.load(item.imageUrl).into(itemView.imageView)
        }
    }
}
