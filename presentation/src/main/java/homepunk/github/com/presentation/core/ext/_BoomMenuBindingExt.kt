package homepunk.github.com.presentation.core.ext

import android.graphics.Color
import android.graphics.Rect
import androidx.databinding.BindingAdapter
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton
import com.nightonke.boommenu.BoomMenuButton
import com.nightonke.boommenu.ButtonEnum
import com.nightonke.boommenu.Piece.PiecePlaceEnum
import homepunk.github.com.presentation.common.model.AppModeModel
import homepunk.github.com.presentation.feature.widget.countrypicker.CountryModel
import homepunk.github.com.presentation.util.DimensionUtil

/**Created by Homepunk on 17.01.2019. **/

@BindingAdapter(
        requireAll = false,
        value = [
            "menuList",
            "menuIconPaddingLeft",
            "menuIconPaddingRight",
            "menuIconPaddingTop",
            "menuIconPaddingBottom"
        ])
fun BoomMenuButton.bindAppModeMenu(menuList: List<AppModeModel>, iconPaddingLeft: Float = 0f, iconPaddingRight: Float = 0f, iconPaddingTop: Float = 0f, iconPaddingBottom: Float = 0f) {
    if (menuList.size != 3) {
        throw ArrayIndexOutOfBoundsException("Menu buttons count = ${menuList.size}, please pass 3 buttons or change PiecePlaceEnum.DOT_X_N to X match count of buttons")
    } else {
        buttonEnum = ButtonEnum.TextOutsideCircle
        piecePlaceEnum = PiecePlaceEnum.DOT_3_1
        buttonPlaceEnum = ButtonPlaceEnum.Horizontal
    }

    menuList.forEach { appMode ->
        val builder = TextOutsideCircleButton.Builder()
                .normalImageRes(appMode.iconResId)
                .buttonRadius(DimensionUtil.dpToPx(this@bindAppModeMenu.context, 36f))
                .normalColorRes(appMode.colorResId)
                .imagePadding(Rect(iconPaddingLeft.toInt(), iconPaddingRight.toInt(), iconPaddingTop.toInt(), iconPaddingBottom.toInt()))
                .normalText(appMode.title)

        addBuilder(builder)
    }
}

@BindingAdapter("countryList")
fun BoomMenuButton.bindCountryList(countryList: List<CountryModel>) {
    piecePlaceEnum = PiecePlaceEnum.DOT_3_1
    buttonPlaceEnum = ButtonPlaceEnum.Horizontal

    val padding = DimensionUtil.dpToPx<Int>(context, 4f)
    countryList.forEach { countryModel ->
        val builder = TextOutsideCircleButton.Builder()
                .normalImageDrawable(countryModel.getFlagDrawable(context))
                .imagePadding(Rect(0, padding, padding, padding))
                .normalColor(Color.TRANSPARENT)
                .buttonRadius(DimensionUtil.dpToPx(this@bindCountryList.context, 36f))
                .normalText(countryModel.countryName)

        addBuilder(builder)
    }
}