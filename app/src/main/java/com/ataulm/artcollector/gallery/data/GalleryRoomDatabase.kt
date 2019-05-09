package com.ataulm.artcollector.gallery.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity
internal data class DbPainting(
        @PrimaryKey val id: String,
        val title: String,
        val webUrl: String,
        val description: String?,
        val creditLine: String?,
        val imageUrl: String,
        val artist: DbArtist
)

@Entity
internal data class DbArtist(
        @PrimaryKey val id: String,
        val name: String
)

@Database(
        version = 1,
        entities = [
            DbPainting::class,
            DbArtist::class
        ]
)
internal abstract class GalleryRoomDatabase : RoomDatabase() {

    abstract fun galleryDao(): GalleryDao

    @Dao
    internal interface GalleryDao {

        @Query("SELECT * FROM DbPainting")
        fun loadPaintings(): List<DbPainting>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insert(painting: List<DbPainting>)
    }
}
