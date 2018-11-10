package com.ataulm.artcollector.paintings.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.ataulm.artcollector.DataObserver
import com.ataulm.artcollector.EventObserver
import com.ataulm.artcollector.Navigation
import com.ataulm.artcollector.R
import com.ataulm.artcollector.paintings.domain.Painting
import com.ataulm.artcollector.paintings.injectDependencies
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_paintings.*
import javax.inject.Inject

class PaintingsActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModel: PaintingsViewModel

    @Inject
    internal lateinit var picasso: Picasso

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paintings)

        val paintingsAdapter = PaintingsAdapter(picasso) { viewModel.onClick(it) }
        recyclerView.apply {
            adapter = paintingsAdapter
            layoutManager = GridLayoutManager(this@PaintingsActivity, 2)
        }

        viewModel.paintings.observe(this, DataObserver<List<Painting>> { paintings ->
            paintingsAdapter.submitList(paintings)
        })

        viewModel.events.observe(this, EventObserver {
            val painting = it.painting
            startActivity(Navigation.PAINTING.viewIntent(painting.artist.id, painting.id))
        })
    }
}
