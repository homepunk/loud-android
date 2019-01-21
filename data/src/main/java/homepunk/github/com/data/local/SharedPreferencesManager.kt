package homepunk.github.com.data.local

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

/**Created by Homepunk on 18.01.2019. **/
@Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
class SharedPreferencesManager @Inject constructor(mContext: Context) {
    val prefs: SharedPreferences = mContext.getSharedPreferences("homepunk.github.com.prefs", Context.MODE_PRIVATE)

    fun <T> put(key: String, value: T) {
        with(prefs.edit()) {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is Boolean -> putBoolean(key, value)
                else -> throw TypeCastException("Invalid value type cast")
            }
            apply()
        }
    }

    fun <T> get(key: String, defValue: T): T {
        with(prefs) {
            return when (defValue) {
                is String -> getString(key, defValue)
                is Int -> getInt(key, defValue)
                is Long -> getLong(key, defValue)
                is Float -> getFloat(key, defValue)
                is Boolean -> getBoolean(key, defValue)
                else -> throw TypeCastException("Invalid value type cast")
            } as T
        }
    }

    inline fun <reified T> get(key: String): T {
        with(prefs) {
            return when {
                isClass<T>(String::class.java) -> getString(key, "")
                isClass<T>(Int::class.java) -> getInt(key, -1)
                isClass<T>(Long::class.java) -> getLong(key, -1)
                isClass<T>(Float::class.java) -> getFloat(key, -1F)
                isClass<T>(Boolean::class.java) -> getBoolean(key, false)
                else -> throw TypeCastException("Invalid value type cast")
            } as T
        }
    }

    inline fun <reified T> isClass(clazz: Class<*>) = T::class.java.isAssignableFrom(clazz)

    companion object {
        const val KEY_CURRENT_APP_MODE = "KEY_APP_MODE"
    }
}