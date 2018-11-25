package com.ataulm.artcollector.gallery.ui

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ataulm.artcollector.R

class GallerySpacingItemDecoration(resources: Resources) : RecyclerView.ItemDecoration() {

    private val offsetPx: Int = resources.getDimensionPixelSize(R.dimen.gallery_spacing_half)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(offsetPx, offsetPx, offsetPx, offsetPx)
    }
}
