package homepunk.github.com.domain.model.songkick

data class SongkickVenue(
	val zip: String = "",
	val website: String = "",
	val lng: Double? = null,
	val city: SongkickCity? = null,
	val displayName: String = "",
	val description: String = "",
	val uri: String = "",
	val capacity: Int? = null,
	val phone: String = "",
	val metroArea: SongkickMetroArea? = null,
	val street: String = "",
	val id: Int? = null,
	val lat: Double? = null
)
