package homepunk.github.com.domain.base

import homepunk.github.com.domain.repository.DiscogsDatabaseRepository

/**Created by Homepunk on 28.12.2018. **/
abstract class BaseDiscogsInteractor(val discogsDatabaseRepository: DiscogsDatabaseRepository) {
    val SEARCH_KEY_QUERY = "q"
    val SEARCH_KEY_TYPE = "type"
    val SEARCH_KEY_TITLE = "title"
    val SEARCH_KEY_RELEASE_TITLE = "release_title"
    val SEARCH_KEY_CREDIT = "credit"
    val SEARCH_KEY_ARTIST = "artist"
    val SEARCH_KEY_ANV = "anv"
    val SEARCH_KEY_LABEL = "label"
    val SEARCH_KEY_GENRE = "genre"
    val SEARCH_KEY_STYLE = "style"
    val SEARCH_KEY_COUNTRY = "country"
    val SEARCH_KEY_YEAR = "year"
    val SEARCH_KEY_FORMAT = "format"
    val SEARCH_KEY_CATNO = "catno"
    val SEARCH_KEY_BARCODE = "barcode"
    val SEARCH_KEY_TRACK = "track"
    val SEARCH_KEY_SUBMITTER = "submitter"
    val SEARCH_KEY_CONTRIBUTOR = "contributor"

    val SEARCH_VALUE_RELEASE = "release"
    val SEARCH_VALUE_MASTER = "master"
    val SEARCH_VALUE_ARTIST = "artist"
    val SEARCH_VALUE_LABEL = "label"
}