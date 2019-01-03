package homepunk.github.com.presentation.core.dagger.module

import dagger.Binds
import dagger.Module
import homepunk.github.com.data.remote.repository.DiscogsDatabaseRemoteRepository
import homepunk.github.com.data.remote.repository.SongkickLocationDataRepository
import homepunk.github.com.data.remote.repository.SongkickEventDataRepository
import homepunk.github.com.domain.repository.DiscogsDatabaseRepository
import homepunk.github.com.domain.repository.LocationRepository
import homepunk.github.com.domain.repository.SongkickEventRepository

/**Created by Homepunk on 27.12.2018. **/
@Module
interface RepositoryBindingModule {
    @Binds
    fun bindDiscogsDatabaseRepository(repo: DiscogsDatabaseRemoteRepository): DiscogsDatabaseRepository

    @Binds
    fun bindSongkickEventRepository(repo: SongkickEventDataRepository): SongkickEventRepository

    @Binds
    fun bindLocationRepository(repo: SongkickLocationDataRepository): LocationRepository
}