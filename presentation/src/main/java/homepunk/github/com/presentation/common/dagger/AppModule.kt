package homepunk.github.com.presentation.common.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private var mContext: Context) {

    @Provides
    @Singleton
    fun providesContext(): Context {
        return mContext
    }
}