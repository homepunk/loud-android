package homepunk.github.com.presentation.core.dagger.module

import dagger.Binds
import dagger.Module
import homepunk.github.com.common.repository.DiscogsRepository
import homepunk.github.com.data.repository.DiscogsDataRepository

/**Created by Homepunk on 27.12.2018. **/
@Module
interface RepositoryBindingModule {
    @Binds
    fun dataRepository(dataRepository: DiscogsDataRepository): DiscogsRepository
}