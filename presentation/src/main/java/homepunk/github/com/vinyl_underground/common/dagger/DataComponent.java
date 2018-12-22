package homepunk.github.com.vinyl_underground.common.dagger;


import homepunk.github.com.data.common.dagger.DataModule;
import homepunk.github.com.vinyl_underground.VUApplication;
import homepunk.github.com.vinyl_underground.feature.main.home.HomeFragmentViewModel;

//@Singleton
//@Component(modules = {DataModule.class})
public interface DataComponent {
    void inject(VUApplication application);
    void inject(HomeFragmentViewModel viewModel);

}
