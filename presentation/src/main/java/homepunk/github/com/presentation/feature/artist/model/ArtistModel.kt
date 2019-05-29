package homepunk.github.com.presentation.feature.artist.model

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableBoolean

/**Created by Homepunk on 27.05.2019. **/
data class ArtistModel(var displayName: String? = "",
                       var id: Long,
                       var isSelected: Boolean) : BaseObservable() {

    var isArtistClicked = ObservableBoolean(isSelected)
    var isPlayClicked = ObservableBoolean(false)
}