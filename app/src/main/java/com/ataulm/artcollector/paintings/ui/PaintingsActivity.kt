package com.ataulm.artcollector.paintings.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
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

        val paintingsAdapter = PaintingsAdapter(picasso)
        recyclerView.apply {
            adapter = paintingsAdapter
            layoutManager = GridLayoutManager(this@PaintingsActivity, 2)
        }

        viewModel.paintings.observe(this, Observer<List<Painting>> { paintings ->
            paintings?.let { paintingsAdapter.submitList(it) }
        })
    }
}

