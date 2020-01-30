/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/30/20 1:04 PM
 */

package com.rikvanvelzen.codingtest.common.dependencyinjection.application;

import com.rikvanvelzen.codingtest.common.dependencyinjection.presentation.PresentationComponent;
import com.rikvanvelzen.codingtest.common.dependencyinjection.presentation.PresentationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkingModule.class})
public interface ApplicationComponent {

    public PresentationComponent newPresentationComponent(PresentationModule presentationModule);
//    public ServiceComponent newServiceComponent(ServiceModule serviceModule);
}
