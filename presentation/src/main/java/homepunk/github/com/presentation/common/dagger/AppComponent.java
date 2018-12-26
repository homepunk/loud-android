package homepunk.github.com.presentation.common.dagger;

import javax.inject.Singleton;

import dagger.Component;
import homepunk.github.com.data.common.dagger.DataComponent;
import homepunk.github.com.data.common.dagger.DataModule;
import homepunk.github.com.presentation.feature.main.home.HomeFragment;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(HomeFragment fragment);
    DataComponent newDataComponent(DataModule module);
}