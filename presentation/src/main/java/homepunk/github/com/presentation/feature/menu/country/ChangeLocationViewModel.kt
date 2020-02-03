package homepunk.github.com.presentation.feature.menu.country

import android.annotation.SuppressLint
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import homepunk.github.com.domain.interactor.UserConfigurationInteractor
import homepunk.github.com.domain.repository.SongkickLocationRepository
import homepunk.github.com.presentation.common.data.AppDataFactory
import homepunk.github.com.presentation.common.model.CountryModel
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.core.ext.swap
import homepunk.github.com.presentation.core.listener.OnItemClickListener
import homepunk.github.com.presentation.core.listener.OnParentChildClickListener
import homepunk.github.com.presentation.feature.menu.country.model.CityBindingChildModel
import homepunk.github.com.presentation.feature.menu.country.model.CountryBindingParentModel
import homepunk.github.com.presentation.feature.menu.country.model.CountryViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

/**Created by Homepunk on 17.01.2019. **/
class ChangeLocationViewModel @Inject constructor(appDataFactory: AppDataFactory,
                                                  private var songkickLocationRepository: SongkickLocationRepository,
                                                  private var userConfigurationInteractor: UserConfigurationInteractor) : BaseViewModel() {

    var isLocationChangeMode = ObservableBoolean(false)

    val currentCountry = ObservableField<CountryViewModel>()
    var currentCountryIndex = MutableLiveData<Int>()

    var favoriteCountries = ObservableArrayList<CountryViewModel>()
    var onFavoriteCountryClickListener: OnItemClickListener<CountryViewModel> = object : OnItemClickListener<CountryViewModel> {
        override fun onClick(position: Int, item: CountryViewModel) {
            if (isLocationChangeMode.get()) {
                if (currentCountry.get() != null &&
                        item.countryModel.countryName != currentCountry.get()!!.countryModel.countryName) {
                    currentCountry.get()!!.isSelected.set(false)
                }

                currentCountryIndex.value = position
                currentCountry.set(item.apply { isSelected.set(true) })
                userLocationPublisher.onNext(item)
            } else {
                isLocationChangeMode.swap()
            }
        }
    }

    init {
        appDataFactory.getCountryModelList().run {
            sortedBy { countryModel: CountryModel -> countryModel.countryName == CountryModel.UA.countryName }
                    .forEach { countryModel ->
                        val userLocationsObservable = userConfigurationInteractor.getUserConfiguration(0)
                                .map { it.locations }

                        val songkickLocationsObservable = songkickLocationRepository.getSongkickLocationByQuery(countryModel.countryName)
                                .filter { it.countryName == countryModel.countryName }
                                .toSortedList { o1, o2 -> o1.locationName.compareTo(o2.locationName) }
                                .toObservable()

                        var i = 0
                        subscriptions.add(songkickLocationsObservable/*.withLatestFrom(userLocationsObservable,
                        BiFunction { locations: List<CityLocation>, savedLocations: List<CityLocation> ->
                            CountryViewModel(countryModel, locations, savedLocations)
                        })*/
                                .map { CountryViewModel(countryModel, isLocationChangeMode, it, it) }
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe { viewModel ->
                                    i++
                                    if (countryModel.countryName == CountryModel.UA.countryName) {
                                        currentCountryIndex.value = i
                                        currentCountry.set(viewModel.apply {
                                            isSelected.set(true)
                                        })
                                    }

                                    favoriteCountries.add(viewModel)
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

    companion object {
        val userLocationPublisher = PublishSubject.create<CountryViewModel>()

    }
}