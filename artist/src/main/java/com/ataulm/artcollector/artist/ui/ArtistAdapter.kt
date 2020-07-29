package com.ataulm.artcollector.artist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ataulm.artcollector.artist.R
import com.ataulm.artcollector.Painting
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.itemview_artist_painting.view.*

internal class ArtistAdapter constructor(
        private val glideRequestManager: RequestManager,
        private val onClick: (Int) -> Unit
) : ListAdapter<com.ataulm.artcollector.Painting, ArtistAdapter.PaintingViewHolder>(PaintingDiffer) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaintingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemview_artist_painting, parent, false)
        return PaintingViewHolder(glideRequestManager, onClick, view)
    }

    override fun onBindViewHolder(viewHolder: PaintingViewHolder, position: Int) = viewHolder.bind(getItem(position))

    object PaintingDiffer : DiffUtil.ItemCallback<com.ataulm.artcollector.Painting>() {
        override fun areItemsTheSame(oldItem: com.ataulm.artcollector.Painting, newItem: com.ataulm.artcollector.Painting) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: com.ataulm.artcollector.Painting, newItem: com.ataulm.artcollector.Painting) = oldItem == newItem
    }

    internal class PaintingViewHolder(
            private val glideRequestManager: RequestManager,
            private val onClick: (Int) -> Unit,
            view: View
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: com.ataulm.artcollector.Painting) {
            itemView.setOnClickListener { onClick(adapterPosition) }
            glideRequestManager.load(item.imageUrl).into(itemView.imageView)
        }
    }
}
