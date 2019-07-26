package homepunk.github.com.presentation.feature.menu.country

import android.annotation.SuppressLint
import androidx.databinding.ObservableArrayList
import homepunk.github.com.domain.interactor.UserConfigurationInteractor
import homepunk.github.com.domain.model.internal.UserLocation
import homepunk.github.com.domain.repository.SongkickLocationRepository
import homepunk.github.com.presentation.common.data.AppDataFactory
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.core.ext.swap
import homepunk.github.com.presentation.core.listener.OnParentChildClickListener
import homepunk.github.com.presentation.feature.menu.country.model.CityBindingChildModel
import homepunk.github.com.presentation.feature.menu.country.model.CountryBindingParentModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import timber.log.Timber
import javax.inject.Inject

/**Created by Homepunk on 17.01.2019. **/
class CountryListViewModel @Inject constructor(appDataFactory: AppDataFactory,
                                               private var songkickLocationRepository: SongkickLocationRepository,
                                               private var userConfigurationInteractor: UserConfigurationInteractor) : BaseViewModel() {

    var itemList = ObservableArrayList<CountryBindingParentModel>()

    init {
        appDataFactory.getCountryModelList().run {
            forEach { countryModel ->
                val userLocationsObservable = userConfigurationInteractor.getUserConfiguration(0)
                        .map { it.locations }
                        .doOnNext { Timber.w("RECEIVE NEXT LOCATIONS = ${it.size}") }

                val locationsObservable = songkickLocationRepository.getSongkickLocationByQuery(countryModel.countryName)

                subscriptions.add(locationsObservable.withLatestFrom(userLocationsObservable,
                        BiFunction { location: UserLocation, countryLocationUserList: List<UserLocation> ->
                            CityBindingChildModel(location, countryLocationUserList.contains(location))
                        })
//                        .map { CityBindingChildModel(it, false) }
                        .toSortedList { o1, o2 ->
                            if (!o1.isSelected.get() && o2.isSelected.get()) 1
                            else if (o1.isSelected.get() && !o2.isSelected.get()) -1
                            else o1.name.get()!!.compareTo(o2.name.get()!!)
                        }
                        .map { CountryBindingParentModel(countryModel, it) }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { parent ->
                            itemList.add(parent)
                        })
            }
        }
    }

    var onParentChildClickListener = object : OnParentChildClickListener<CountryBindingParentModel, CityBindingChildModel> {
        @SuppressLint("CheckResult")
        override fun onClick(position: Int, parent: CountryBindingParentModel, childPosition: Int, child: CityBindingChildModel) {
            Timber.w("onClick $position, PARENT: ${parent.countryModel.countryName} -> CHILD: ${child.name.get()}")
            if (child.isSelected.swap()) {
                userConfigurationInteractor.addUserLocation(child.location)
            } else {
                userConfigurationInteractor.removeUserLocation(child.location)
            }
        }
    }
}