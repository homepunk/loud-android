package homepunk.github.com.presentation.common.model;

import androidx.databinding.ObservableBoolean;

/**
 * Created by Homepunk on 24.01.2019.
 **/
public class BooleanHolder {
    public static ObservableBoolean value = new ObservableBoolean(false);

    public static void swap() {
        value.set(!value.get());
    }

    public void set(boolean value) {
        BooleanHolder.value.set(value);
    }
}
