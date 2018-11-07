package com.ataulm.artcollector.paintings.ui

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ataulm.artcollector.R
import com.ataulm.artcollector.paintings.domain.Painting
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.itemview_painting.view.*

internal class PaintingsAdapter constructor(
        private val picasso: Picasso
) : ListAdapter<Painting, PaintingsAdapter.PaintingViewHolder>(PaintingDiffer) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaintingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemview_painting, parent, false)
        return PaintingViewHolder(picasso, view)
    }

    override fun onBindViewHolder(viewHolder: PaintingViewHolder, position: Int) = viewHolder.bind(getItem(position))

    object PaintingDiffer : DiffUtil.ItemCallback<Painting>() {
        override fun areItemsTheSame(oldItem: Painting, newItem: Painting) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Painting, newItem: Painting) = oldItem == newItem
    }

    internal class PaintingViewHolder(private val picasso: Picasso, view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: Painting) {
            picasso.load(item.imageUrl).into(itemView.imageView)
        }
    }
}
