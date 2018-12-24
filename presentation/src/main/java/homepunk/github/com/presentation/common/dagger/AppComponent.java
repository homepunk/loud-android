package homepunk.github.com.presentation.common.dagger;

import javax.inject.Singleton;

import dagger.Component;
import homepunk.github.com.presentation.VUApplication;
import homepunk.github.com.presentation.feature.main.home.HomeFragmentViewModel;

@Component(modules = {AppModule.class, DataModule.class})
@Singleton
public interface AppComponent {
    void inject(HomeFragmentViewModel viewModel);
    void inject(VUApplication application);
}