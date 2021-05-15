package bangkit.adhytia.moviecatalogue.data

import bangkit.adhytia.moviecatalogue.utils.Constants
import bangkit.adhytia.moviecatalogue.utils.JsonHelper
import org.json.JSONException
import org.json.JSONObject

class DataSource private constructor(private val jsonHelper: JsonHelper) {
    companion object {
        @Volatile
        private var instance: DataSource? = null

        fun getInstance(helper: JsonHelper): DataSource =
            instance ?: synchronized(this) {
                instance ?: DataSource(helper).apply { instance = this }
            }
    }

    fun getMovieList(): ArrayList<MovieEntity> {
        val list = arrayListOf<MovieEntity>()
        try {
            val obj = JSONObject(jsonHelper.loadJSONFromAsset("popularmovies.json"))
            val movieArray = obj.getJSONArray("results")
            for (i in 0 until movieArray.length()) {
                val movieDetail = movieArray.getJSONObject(i)

                var posterPath = movieDetail.getString("poster_path")
                var backdropPath = movieDetail.getString("backdrop_path")

                posterPath =
                    if (posterPath.isNullOrBlank()) Constants.NO_IMAGE_URL else Constants.BASE_IMAGE_URL + posterPath
                backdropPath =
                    if (backdropPath.isNullOrBlank()) Constants.NO_IMAGE_URL else Constants.BASE_IMAGE_URL + backdropPath

                val movie = MovieEntity(
                    movieDetail.getInt("id"),
                    movieDetail.getString("title"),
                    movieDetail.getString("overview"),
                    posterPath,
                    backdropPath,
                    movieDetail.getString("release_date"),
                    movieDetail.getString("vote_average"),
                    movieDetail.getString("vote_count"),
                    movieDetail.getString("popularity")
                )

                list.add(movie)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return list
    }

    fun getTvShowList(): ArrayList<TvShowEntity> {
        val list = arrayListOf<TvShowEntity>()
        try {
            val obj = JSONObject(jsonHelper.loadJSONFromAsset("populartvshows.json"))
            val tvshowArray = obj.getJSONArray("results")
            for (i in 0 until tvshowArray.length()) {
                val tvshowDetail = tvshowArray.getJSONObject(i)

                var posterPath = tvshowDetail.getString("poster_path")
                var backdropPath = tvshowDetail.getString("backdrop_path")

                posterPath =
                    if (posterPath.isNullOrBlank()) Constants.NO_IMAGE_URL else Constants.BASE_IMAGE_URL + posterPath
                backdropPath =
                    if (backdropPath.isNullOrBlank()) Constants.NO_IMAGE_URL else Constants.BASE_IMAGE_URL + backdropPath

                val tvshow = TvShowEntity(
                    tvshowDetail.getInt("id"),
                    tvshowDetail.getString("name"),
                    tvshowDetail.getString("overview"),
                    posterPath,
                    backdropPath,
                    tvshowDetail.getString("first_air_date"),
                    tvshowDetail.getString("vote_average"),
                    tvshowDetail.getString("vote_count"),
                    tvshowDetail.getString("popularity")
                )

                list.add(tvshow)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return list
    }

}