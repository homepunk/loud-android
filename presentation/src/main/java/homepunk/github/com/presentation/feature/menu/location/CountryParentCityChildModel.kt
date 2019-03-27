package homepunk.github.com.presentation.feature.menu.location

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableBoolean
import homepunk.github.com.presentation.core.ext.swap
import homepunk.github.com.presentation.feature.widget.countrypicker.CountryModel

/**Created by Homepunk on 14.03.2019. **/
class CountryParentCityChildModel(val countryModel: CountryModel) : BaseObservable() {
    var isParentExpanded = ObservableBoolean(false)

    @Bindable
    fun getOnParentClickListener() = View.OnClickListener { isParentExpanded.swap() }

    @Bindable
    fun getCityList() = arrayListOf("Kharkiv", "Kiev", "Lviv", "Odessa")
}