/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/25/20 11:56 AM
 */

package com.rikvanvelzen.codingtest.ui.screens.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

import java.lang.reflect.ParameterizedType;

public abstract class MvvmBaseActivity<B extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity {

    public final String TAG = getClass().getSimpleName();

    protected B binding;
    protected VM viewModel;

    /******************************************************
     * Abstract methods
     ******************************************************/

    @LayoutRes
    protected abstract int getLayoutResource();

    /******************************************************
     * Activity override methods
     ******************************************************/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, getLayoutResource());
        viewModel = ViewModelProviders.of(this).get(getViewModelClass());
        binding.setVariable(com.rikvanvelzen.codingtest.BR.viewModel, viewModel);
        binding.setLifecycleOwner(this);

        setupBaseViewModelObservers();
    }

    /******************************************************
     * Private methods
     ******************************************************/

    private void setupBaseViewModelObservers() {

//        viewModel.shouldNavigateBack().observe(this, navigate -> onBackPressed());
    }

    @SuppressWarnings("unchecked")
    private Class<VM> getViewModelClass() {

        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<VM>) parameterizedType.getActualTypeArguments()[1];
    }
}
