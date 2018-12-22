package homepunk.github.com.vinyl_underground.feature.main.home

import android.content.Context
import homepunk.github.com.data.repository.DiscogsDataRepository
import homepunk.github.com.vinyl_underground.base.BaseViweModel

class HomeFragmentViewModel(mContext: Context?) : BaseViweModel(mContext) {
//    @Inject
    internal lateinit var discogsRepository: DiscogsDataRepository

    override fun init() {
        discogsRepository = DiscogsDataRepository(null)
    }
}
