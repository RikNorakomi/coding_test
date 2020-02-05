/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/23/20 4:24 PM
 */

package com.rikvanvelzen.codingtest.ui.screens.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import java.lang.reflect.ParameterizedType;

import javax.inject.Inject;

public abstract class MvvmBaseFragment<B extends ViewDataBinding, VM extends BaseViewModel> extends Fragment {

    public final String TAG = getClass().getSimpleName();

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    protected B binding;
    protected VM viewModel;

    /******************************************************
     * Abstract methods and optional overridable methods
     ******************************************************/

    @LayoutRes
    protected abstract int getLayoutResource();

    private ViewModelProvider getViewModelProvider() {
        return getActivity() != null ? ViewModelProviders.of(getActivity(), viewModelFactory)
                : ViewModelProviders.of(this, viewModelFactory);
    }

    /******************************************************
     * Fragment override methods
     ******************************************************/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutResource(), container, false);
        viewModel = getViewModelProvider().get(getViewModelClass());
        binding.setVariable(com.rikvanvelzen.codingtest.BR.viewModel, viewModel);
        binding.setLifecycleOwner(this);

        setupBaseViewModelObservers();

        return binding.getRoot();
    }

    /******************************************************
     * Private methods
     ******************************************************/

    private void setupBaseViewModelObservers() {

        viewModel.shouldNavigateBack().observe(this, navigate -> getActivity().onBackPressed());
    }

    @SuppressWarnings("unchecked")
    private Class<VM> getViewModelClass() {

        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<VM>) parameterizedType.getActualTypeArguments()[1];
    }
}
