package bangkit.adhytia.moviecatalogue.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "posterURL")
    val posterURL: String,

    @ColumnInfo(name = "backdropURL")
    val backdropURL: String,

    @ColumnInfo(name = "releaseDate")
    val releaseDate: String,

    @ColumnInfo(name = "voteAverage")
    val voteAverage: String,

    @ColumnInfo(name = "voteCount")
    val voteCount: String,

    @ColumnInfo(name = "popularity")
    val popularity: String
)