package com.ataulm.artcollector.artist.domain

import javax.inject.Inject

internal class GetArtistUseCase @Inject constructor(
        private val repository: ArtistRepository
) {

    suspend operator fun invoke() = repository.artist()
}
