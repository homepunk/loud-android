package homepunk.github.com.data.common.dagger

import android.content.Context
import android.text.TextUtils
import com.google.gson.GsonBuilder
import homepunk.github.com.data.Constant
import homepunk.github.com.data.remote.DiscogsApi
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

//@Module
class DataModule {
//    @Provides
//    @Singleton
    fun provideDiscogsApi(okHttpClient: OkHttpClient): DiscogsApi {
        synchronized(DiscogsApi::class.java) {
            val gson = GsonBuilder()
                    .setLenient()
                    .create()

            val retrofit = Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build()
            return retrofit.create(DiscogsApi::class.java)
        }
    }

//    @Provides
//    @Singleton
    fun provideOkhttp(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.interceptors().add(logging)
        httpClient.writeTimeout((15 * 1000).toLong(), TimeUnit.MILLISECONDS)
        httpClient.readTimeout((15 * 1000).toLong(), TimeUnit.MILLISECONDS)
        httpClient.connectTimeout((15 * 1000).toLong(), TimeUnit.MILLISECONDS)
        return httpClient.build()
    }

}