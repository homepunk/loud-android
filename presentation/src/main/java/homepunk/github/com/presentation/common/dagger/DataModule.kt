package homepunk.github.com.presentation.common.dagger

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import homepunk.github.com.data.Constant
import homepunk.github.com.data.Constant.CURRENT_TOKEN
import homepunk.github.com.data.Constant.USER_AGENT
import homepunk.github.com.data.remote.DiscogsApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor {
                val original = it.request()
                val request = original.newBuilder()
                        .header("User-Agent", USER_AGENT)
                        .header("Authorization", "Discogs token=%s".format(CURRENT_TOKEN))
                        .method(original.method(), original.body())
                        .build()
                it.proceed(request)
            }
            .writeTimeout((15 * 1000).toLong(), TimeUnit.MILLISECONDS)
            .readTimeout((15 * 1000).toLong(), TimeUnit.MILLISECONDS)
            .connectTimeout((15 * 1000).toLong(), TimeUnit.MILLISECONDS)
            .build()

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideDiscogsApi(client: OkHttpClient, gson: Gson): DiscogsApi = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(DiscogsApi::class.java)
}