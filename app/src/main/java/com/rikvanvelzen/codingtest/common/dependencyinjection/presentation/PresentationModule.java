/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/30/20 1:04 PM
 */

package com.rikvanvelzen.codingtest.common.dependencyinjection.presentation;

import com.rikvanvelzen.codingtest.data.api.CurrencyService;
import com.rikvanvelzen.codingtest.data.repositories.CurrencyRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class PresentationModule {

//    private final FragmentActivity mActivity;
//
//    public PresentationModule(FragmentActivity fragmentActivity) {
//        mActivity = fragmentActivity;
//    }
//
//    @Provides
//    FragmentManager getFragmentManager() {
//        return mActivity.getSupportFragmentManager();
//    }
//
//    @Provides
//    LayoutInflater getLayoutInflater() {
//        return LayoutInflater.from(mActivity);
//    }
//
//    @Provides
//    Activity getActivity() {
//        return mActivity;
//    }
//
//    @Provides
//    Context context(Activity activity) {
//        return activity;
//    }
//
//    @Provides
//    CurrencyRepository getCurrencyRepository(CurrencyService currencyService) {
//        return new CurrencyRepository(currencyService);
//    }
//
//    @Provides
//    DialogsManager getDialogsManager(FragmentManager fragmentManager) {
//        return new DialogsManager(fragmentManager);
//    }
//
//    @Provides
//    ImageLoader getImageLoader(Activity activity) {
//        return new ImageLoader(activity);
//    }
}
