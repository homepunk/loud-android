package homepunk.github.com.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import saschpe.discogs.model.database.Urls

class Pagination {
    var perPage: Int = 0
    var pages: Int = 0
    var page: Int = 0
//    var urls: Urls? = null
    var items: Int = 0
}