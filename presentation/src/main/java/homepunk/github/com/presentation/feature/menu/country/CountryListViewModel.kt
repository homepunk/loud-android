package homepunk.github.com.presentation.feature.menu.country

import android.annotation.SuppressLint
import androidx.databinding.ObservableArrayList
import homepunk.github.com.domain.interactor.UserLocationSettingsInteractor
import homepunk.github.com.domain.model.songkick.SongkickLocation
import homepunk.github.com.domain.repository.LocationRepository
import homepunk.github.com.presentation.common.data.AppDataFactory
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.core.ext.swap
import homepunk.github.com.presentation.core.listener.OnParentChildClickListener
import homepunk.github.com.presentation.feature.menu.country.model.CityBindingChildModel
import homepunk.github.com.presentation.feature.menu.country.model.CountryBindingParentModel
import io.reactivex.functions.BiFunction
import timber.log.Timber
import javax.inject.Inject

/**Created by Homepunk on 17.01.2019. **/
class CountryListViewModel @Inject constructor(appDataFactory: AppDataFactory,
                                               private var locationRepository: LocationRepository,
                                               private var userLocationSettingsInteractor: UserLocationSettingsInteractor) : BaseViewModel() {

    var itemList = ObservableArrayList<CountryBindingParentModel>()

    init {
        appDataFactory.getCountryModelList().run {
            forEach { countryModel ->
                val userLocationListObservable = userLocationSettingsInteractor.getCountryUserLocationSettings(countryModel.countryName)
                        .doOnNext { Timber.w("CHANGED: ${it.currentUserCountryName}, CITY COUNT: ${it.currentUserLocationList.size}") }
                        .map { it.currentUserLocationList }


                val locationObservable = locationRepository.getSongkickLocationByQuery(countryModel.countryName)
                        .filter { it.city != null }
                        .doOnNext { Timber.w("ON NEXT LOCATION: ${it.city?.displayName}") }

                locationObservable.withLatestFrom(userLocationListObservable,
                        BiFunction { location: SongkickLocation, countryLocationUserList: List<SongkickLocation> ->
                            CityBindingChildModel(location, countryLocationUserList.contains(location))
                        })
                        .toList()
                        .map { CountryBindingParentModel(countryModel, it) }
                        .subscribe { parent ->
                            itemList.add(parent)
                        }
            }
        }
    }

    var onParentChildClickListener = object : OnParentChildClickListener<CityBindingChildModel, CountryBindingParentModel> {
        @SuppressLint("CheckResult")
        override fun onClick(position: Int, child: CityBindingChildModel, parent: CountryBindingParentModel) {
            Timber.w("onClick $position, PARENT: ${parent.countryModel.countryName} -> CHILD: ${child.name.get()}")
            child.isSelected.swap()
            userLocationSettingsInteractor.saveUserLocationForCountry(parent.countryModel.countryName, child.location)
        }
    }
}