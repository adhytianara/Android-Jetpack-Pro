package bangkit.adhytia.moviecatalogue.data

data class MovieEntity(
    var id: Int,
    val title: String,
    val overview: String,
    val posterURL: String,
    val backdropURL: String,
    val releaseDate: String,
    val voteAverage: String,
    val voteCount: String,
    val popularity: String
)