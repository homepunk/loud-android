package homepunk.github.com.presentation.common.dagger;


import javax.inject.Singleton;

import dagger.Component;
import homepunk.github.com.presentation.VUApplication;
import homepunk.github.com.presentation.feature.main.home.HomeFragmentViewModel;

//@Singleton
//@Component(modules = {DataModule.class})
public interface DataComponent {
    void inject(VUApplication application);
    void inject(HomeFragmentViewModel viewModel);

}
