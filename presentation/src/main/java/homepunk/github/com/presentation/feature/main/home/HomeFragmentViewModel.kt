package homepunk.github.com.presentation.feature.main.home

import android.content.Context
import homepunk.github.com.common.repository.DiscogsRepository
import homepunk.github.com.presentation.core.base.BaseViweModel
import javax.inject.Inject

class HomeFragmentViewModel @Inject constructor(mContext: Context)
    : BaseViweModel(mContext) {

    @Inject
    lateinit var discogsRepository: DiscogsRepository

    fun getReleaseList() = discogsRepository.getReleaseList()
}
