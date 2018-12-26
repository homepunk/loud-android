package homepunk.github.com.data.common.dagger;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Subcomponent;
import homepunk.github.com.data.common.dagger.DataScope;
import homepunk.github.com.data.common.dagger.DataModule;

@Subcomponent(modules = {DataModule.class})
@DataScope
public interface DataComponent {
    Context context();

}
