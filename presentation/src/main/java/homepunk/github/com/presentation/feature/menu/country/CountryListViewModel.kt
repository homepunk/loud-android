package homepunk.github.com.presentation.feature.menu.country

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import homepunk.github.com.domain.interactor.UserConfigurationInteractor
import homepunk.github.com.domain.model.internal.UserLocation
import homepunk.github.com.domain.repository.SongkickLocationRepository
import homepunk.github.com.presentation.common.data.AppDataFactory
import homepunk.github.com.presentation.common.model.CountryModel
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.core.ext.swap
import homepunk.github.com.presentation.core.listener.OnItemClickListener
import homepunk.github.com.presentation.core.listener.OnParentChildClickListener
import homepunk.github.com.presentation.feature.menu.country.model.CityBindingChildModel
import homepunk.github.com.presentation.feature.menu.country.model.CountryBindingParentModel
import homepunk.github.com.presentation.feature.menu.country.model.CountryLocationModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

/**Created by Homepunk on 17.01.2019. **/
class CountryListViewModel @Inject constructor(appDataFactory: AppDataFactory,
                                               private var songkickLocationRepository: SongkickLocationRepository,
                                               private var userConfigurationInteractor: UserConfigurationInteractor) : BaseViewModel() {

    //    var userCountryLocationList = ObservableArrayList<UserLocation>()
    val currentUserCountry = ObservableField<CountryLocationModel>()
    val onCurrentCountryClickListener = View.OnClickListener { currentUserCountry.get()?.isParentExpanded?.swap() }

    var itemList = ObservableArrayList<CountryLocationModel>()
    var countryLiveData = MutableLiveData<Int>()
    var onCountryClickListener: OnItemClickListener<CountryLocationModel> = object : OnItemClickListener<CountryLocationModel> {
        override fun onClick(position: Int, item: CountryLocationModel) {
            if (currentUserCountry.get() != null &&
                    item.countryModel.countryName != currentUserCountry.get()!!.countryModel.countryName) {
                currentUserCountry.get()!!.isParentExpanded.swap()
            }
            item.isParentExpanded.swap()
            countryLiveData.value = position
            userLocationPublisher.onNext(item)
            currentUserCountry.set(item)
        }
    }

    companion object {
        val userLocationPublisher = PublishSubject.create<CountryLocationModel>()
    }

    init {
        appDataFactory.getCountryModelList().run {
            forEach { countryModel ->
                val userLocationsObservable = userConfigurationInteractor.getUserConfiguration(0)
                        .map { it.locations }

                val locationsObservable = songkickLocationRepository.getSongkickLocationByQuery(countryModel.countryName)
                        .filter { it.countryName == countryModel.countryName }
                        .toSortedList { o1, o2 -> o1.locationName.compareTo(o2.locationName) }
                        .toObservable()

                var i = 0
                subscriptions.add(locationsObservable.withLatestFrom(userLocationsObservable,
                        BiFunction { locations: List<UserLocation>, savedLocations: List<UserLocation> ->
                            CountryLocationModel(countryModel, locations, savedLocations)
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { parent ->
                            itemList.add(parent)
                            i++
                            if (countryModel.countryName == CountryModel.UA.countryName) {
                                onCountryClickListener.onClick(i, parent)
                            }
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