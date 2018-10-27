package com.ataulm.artcollector.paintings.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ataulm.artcollector.R
import com.ataulm.artcollector.paintings.injectDependencies
import javax.inject.Inject

class PaintingsActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: PaintingsViewModelFactory

    private lateinit var viewModel: PaintingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paintings)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(PaintingsViewModel::class.java)
    }
}

