package homepunk.github.com.presentation.core.dagger.module

import dagger.Binds
import dagger.Module
import homepunk.github.com.data.local.repository.AppModeDataRepository
import homepunk.github.com.data.local.repository.UserConfigurationDataRepository
import homepunk.github.com.data.remote.repository.DiscogsDatabaseRemoteRepository
import homepunk.github.com.data.remote.repository.SongkickEventDataRepository
import homepunk.github.com.data.remote.repository.SongkickLocationDataRepository
import homepunk.github.com.data.remote.repository.YoutubeDataRepository
import homepunk.github.com.domain.repository.*

/**Created by Homepunk on 27.12.2018. **/
@Module
interface RepositoryBindingModule {
    @Binds
    fun bindDiscogsDatabaseRepository(repo: DiscogsDatabaseRemoteRepository): DiscogsDatabaseRepository

    @Binds
    fun bindSongkickEventRepository(repo: SongkickEventDataRepository): SongkickEventRepository

    @Binds
    fun bindLocationRepository(repo: SongkickLocationDataRepository): SongkickLocationRepository

    @Binds
    fun bindAppModeDataRepository(repo: AppModeDataRepository): AppModeRepository

    @Binds
    fun bindUserConfigurationRepository(repo: UserConfigurationDataRepository): UserConfigurationRepository

    @Binds
    fun bindYoutubeDataRepository(repo: YoutubeDataRepository): YoutubeRepository
}