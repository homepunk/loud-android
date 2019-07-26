package homepunk.github.com.data.util

import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**Created by Homepunk on 15.07.2019. **/

@WorkerThread
fun runInBackground(function: () -> Unit) {
    runBlocking {
        launch(Dispatchers.IO) {
            function.invoke()
        }
    }
}
