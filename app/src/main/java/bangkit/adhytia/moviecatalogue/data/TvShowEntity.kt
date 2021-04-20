package bangkit.adhytia.moviecatalogue.data

data class TvShowEntity(
    var id: Int,
    val title: String,
    val overview: String,
    val posterURL: String,
    val backdropURL: String,
    val firstAirDate: String,
    val voteAverage: String,
    val voteCount: String,
    val popularity: String
)