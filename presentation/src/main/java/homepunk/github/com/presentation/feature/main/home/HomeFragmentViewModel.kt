package homepunk.github.com.presentation.feature.main.home

import android.content.Context
import homepunk.github.com.data.repository.DiscogsDataRepository
import homepunk.github.com.presentation.base.BaseViweModel
import javax.inject.Inject

class HomeFragmentViewModel(mContext: Context) : BaseViweModel(mContext) {
    @Inject
    lateinit var discogsRepository: DiscogsDataRepository

    fun getReleaseList() = discogsRepository.getReleaseList()
}
