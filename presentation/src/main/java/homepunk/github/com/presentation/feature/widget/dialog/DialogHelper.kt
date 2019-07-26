package homepunk.github.com.presentation.feature.widget.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.RecyclerView
import homepunk.github.com.presentation.R

/**Created by Homepunk on 15.07.2019. **/
object DialogHelper {
    fun getErrorDialog(context: Context): Dialog {
        val d = Dialog(context)
        d.setContentView(R.layout.custom_layout_recycler_dialog)
        val recycler = d.findViewById<RecyclerView>(R.id.recycler)

        d.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return d
    }
}