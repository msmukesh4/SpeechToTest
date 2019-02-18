package com.example.speechtotest.di;


import com.example.speechtotest.ui.home.HomeViewModel;
import com.example.speechtotest.ui.speech.SpeechViewModel;

import dagger.Subcomponent;

/**
 * A sub component to create ViewModels. It is called by the
 * {@link com.example.speechtotest.ui.base.ProjectViewModelFactory}.
 */
@Subcomponent
public interface ViewModelSubComponent {

    @Subcomponent.Builder
    interface Builder {
        ViewModelSubComponent build();
    }

    HomeViewModel getHomeViewModel();
    SpeechViewModel getSpeechViewModel();

}
