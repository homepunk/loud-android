package homepunk.github.com.domain.repository

/**Created by Homepunk on 28.12.2018. **/
interface DiscogsDatabaseRepository {
    fun search(paramsMap: Map<String, String>)
}