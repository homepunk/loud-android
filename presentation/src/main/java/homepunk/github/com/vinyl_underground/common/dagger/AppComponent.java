package homepunk.github.com.vinyl_underground.common.dagger;

import homepunk.github.com.data.common.dagger.DataModule;
import homepunk.github.com.vinyl_underground.feature.main.home.HomeFragmentViewModel;

//@Singleton
//@Component(modules = {AppModule.class, DataModule.class})
public interface AppComponent {
    void inject(HomeFragmentViewModel viewModel);
}