package homepunk.github.com.presentation.util

import java.text.SimpleDateFormat
import java.util.*

/**Created by Homepunk on 16.01.2019. **/
object SongkickUtil {
    fun <T> getSongkickArtistThumb(id: T?) = "https://images.sk-static.com/images/media/profile_images/artists/$id/huge_avatar"

    fun getDate(date: String) = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)
}