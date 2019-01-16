package homepunk.github.com.presentation.feature.main.event.upcoming

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import homepunk.github.com.data.constant.DataFactory

/**Created by Homepunk on 11.01.2019. **/
class UpcomingEventItemViewModel(title: String, imageUrl: String? = "") : ViewModel() {
    val title = ObservableField<String>(title)
    var thumb = ObservableField<String>(DataFactory.getSongkickArtistThumb(imageUrl))
}