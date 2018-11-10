package com.ataulm.artcollector.artist

import android.arch.lifecycle.ViewModelProviders
import com.ataulm.artcollector.artist.data.AndroidArtistRepository
import com.ataulm.artcollector.artist.domain.ArtistRepository
import com.ataulm.artcollector.artist.ui.ArtistActivity
import com.ataulm.artcollector.artist.ui.ArtistViewModel
import com.ataulm.artcollector.artist.ui.ArtistViewModelFactory
import dagger.Module
import dagger.Provides

@Module
internal object ArtistModule {

    @JvmStatic
    @Provides
    fun artistRepository(artistRepository: AndroidArtistRepository): ArtistRepository {
        return artistRepository
    }

    @JvmStatic
    @Provides
    fun viewModel(activity: ArtistActivity, viewModelFactory: ArtistViewModelFactory) =
            ViewModelProviders.of(activity, viewModelFactory).get(ArtistViewModel::class.java)
}
