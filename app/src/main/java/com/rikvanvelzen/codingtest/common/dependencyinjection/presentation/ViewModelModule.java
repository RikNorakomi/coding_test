/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/30/20 1:05 PM
 */

package com.rikvanvelzen.codingtest.common.dependencyinjection.presentation;


import androidx.lifecycle.ViewModel;


import com.rikvanvelzen.codingtest.common.dependencyinjection.viewmodel.ViewModelFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import javax.inject.Provider;

import dagger.MapKey;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public class ViewModelModule {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }

    @Provides
    ViewModelFactory viewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
        return new ViewModelFactory(providerMap);
    }

//    @Provides
//    @IntoMap
//    @ViewModelKey(QuestionDetailsViewModel.class)
//    ViewModel questionDetailsViewModel(FetchQuestionDetailsUseCase fetchQuestionDetailsUseCase) {
//        return new QuestionDetailsViewModel(fetchQuestionDetailsUseCase);
//    }
//
//    @Provides
//    @IntoMap
//    @ViewModelKey(QuestionsListViewModel.class)
//    ViewModel questionsListViewModel() {
//        return new QuestionsListViewModel();
//    }
}
