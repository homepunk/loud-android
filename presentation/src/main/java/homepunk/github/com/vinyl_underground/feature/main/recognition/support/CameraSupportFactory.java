package homepunk.github.com.vinyl_underground.feature.main.recognition.support;

import android.content.Context;
import android.os.Build;

/**
 * Created by Homepunk on 03.01.2018.
 **/

public class CameraSupportFactory {
    public static CameraSupport getCamera(Context context) {
        CameraSupport camera;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            camera = new CameraNew(context);
        } else {
            camera = new CameraOld();
        }
        return camera;
    }
}
