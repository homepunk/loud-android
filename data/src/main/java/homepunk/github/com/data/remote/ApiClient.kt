package homepunk.github.com.data.remote

import com.google.gson.GsonBuilder
import homepunk.github.com.data.core.constant.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**Created by Homepunk on 27.12.2018. **/
class ApiClient {
    class Builder {
        companion object {
            private val gson = GsonBuilder().setLenient().create()

            fun buildDiscogsApi(): DiscogsApi = Retrofit.Builder()
                    .baseUrl(Constant.DISCOGS.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getBaseOkHttpClient()
                            .addInterceptor {
                                val original = it.request()
                                val request = original.newBuilder()
                                        .header("Authorization", "Discogs token=%s".format(Constant.DISCOGS.CURRENT_TOKEN))
                                        .method(original.method(), original.body())
                                        .build()
                                it.proceed(request)
                            }
                            .build())
                    .build()
                    .create(DiscogsApi::class.java)

            fun buildSongkickApi(): SongkickApi = Retrofit.Builder()
                    .baseUrl(Constant.SONGKICK.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getBaseOkHttpClient().build())
                    .build()
                    .create(SongkickApi::class.java)


            private fun getBaseOkHttpClient() = OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .writeTimeout((15 * 1000).toLong(), TimeUnit.MILLISECONDS)
                    .readTimeout((15 * 1000).toLong(), TimeUnit.MILLISECONDS)
                    .connectTimeout((15 * 1000).toLong(), TimeUnit.MILLISECONDS)
        }
    }
}