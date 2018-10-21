package com.ataulm.artcollector.paintings

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ataulm.artcollector.R

class PaintingsActivity : AppCompatActivity() {

    private lateinit var viewModel: PaintingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paintings)

        viewModel = ViewModelProviders.of(this, PaintingsViewModelFactory())
                .get(PaintingsViewModel::class.java)
    }
}

