package homepunk.github.com.data.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import homepunk.github.com.data.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**Created by Homepunk on 27.12.2018. **/
class DiscogsApiBuilder {
    fun build(): DiscogsApi = createDiscogsApi(createOkHttpClient(), createGson())

    private fun createOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor {
                val original = it.request()
                val request = original.newBuilder()
                        .header("User-Agent", Constant.USER_AGENT)
                        .header("Authorization", "Discogs token=%s".format(Constant.CURRENT_TOKEN))
                        .method(original.method(), original.body())
                        .build()
                it.proceed(request)
            }
            .writeTimeout((15 * 1000).toLong(), TimeUnit.MILLISECONDS)
            .readTimeout((15 * 1000).toLong(), TimeUnit.MILLISECONDS)
            .connectTimeout((15 * 1000).toLong(), TimeUnit.MILLISECONDS)
            .build()

    private fun createGson() = GsonBuilder().setLenient().create()

    private fun createDiscogsApi(client: OkHttpClient, gson: Gson): DiscogsApi = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(DiscogsApi::class.java)
    
}