package homepunk.github.com.presentation.core.ext

import android.graphics.drawable.Drawable
import android.view.View
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException

/**Created by Homepunk on 11.01.2019. **/

fun View.getDrawableFromXml(id: Int): Drawable? {
    var drawable: Drawable? = null
    try {
        drawable = Drawable.createFromXml(resources, resources.getXml(id))
    } catch (e: XmlPullParserException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return drawable
}
