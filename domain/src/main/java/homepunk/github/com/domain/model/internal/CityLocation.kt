package homepunk.github.com.domain.model.internal

import java.io.Serializable

/**Created by Homepunk on 29.03.2019. **/
data class CityLocation(val id: Long,
                        val locationName: String,
                        val countryName: String,
                        var isCurrent: Boolean) : Serializable {
    override fun equals(other: Any?): Boolean {
        return (other is CityLocation) &&
                other.id == id &&
                other.locationName == locationName
    }

    override fun toString(): String {
        return "{id = $id, locationName = $locationName, countryName = $countryName, isCurrent = $isCurrent}"
    }
}