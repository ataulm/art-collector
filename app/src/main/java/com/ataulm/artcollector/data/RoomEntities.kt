package com.ataulm.artcollector.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbPainting(
        @PrimaryKey val id: String,
        val title: String,
        val webUrl: String,
        val description: String?,
        val creditLine: String?,
        val imageUrl: String,
        val artist: DbArtist
)

@Entity
data class DbArtist(
        @PrimaryKey val id: String,
        val name: String
)
