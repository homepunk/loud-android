package homepunk.github.com.presentation.core.dagger.module

import dagger.Binds
import dagger.Module
import homepunk.github.com.data.repository.remote.DiscogsDatabaseRemoteRepository
import homepunk.github.com.domain.repository.DiscogsDatabaseRepository

/**Created by Homepunk on 27.12.2018. **/
@Module
interface RepositoryBindingModule {
    @Binds
    fun bindDiscogsDatabaseRepository(repo: DiscogsDatabaseRemoteRepository): DiscogsDatabaseRepository
}