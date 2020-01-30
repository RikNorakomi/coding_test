/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 5/24/19 9:48 AM
 */

package com.rikvanvelzen.codingtest.common;


import androidx.annotation.MainThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java9.util.function.BiFunction;


/**
 * allows Transformation functionality for sets of LiveData
 * Use biMap(NullSafe) for 2 and triMapNullSafe for 3
 */
public class MultipleLiveDataTransformation {

    public interface TriFunction<T, U, V, R> {

        /**
         * Applies this function to the given arguments.
         *
         * @param t the first function argument
         * @param u the second function argument
         * @return the function result
         */
        R apply(T t, U u, V v);
    }

    /**
     * This method provides a LiveData object which observes 2 different Livedata types,
     * allowing you to observe multiple values with a single callback
     * <p>
     * It works as a {@link BiFunction Binary Function} version of the {@link androidx.lifecycle.Transformations Transformations} map method.
     *
     * @param data1 First LiveData object
     * @param data2 Second LiveData object
     * @param biFun BiFunction to perform transformation
     * @param <X>   Type parameter of data1
     * @param <Y>   Type parameter of data2
     * @param <O>   Type parameter of returned LiveData object
     * @return LiveData with the value returned by biFun
     */

    @MainThread
    public static <X, Y, O> LiveData<O> biMap(LiveData<X> data1, LiveData<Y> data2, final BiFunction<X, Y, O> biFun) {
        final MediatorLiveData<O> result = new MediatorLiveData<>();
        result.addSource(data1, x -> result.setValue(biFun.apply(x, data2.getValue())));
        result.addSource(data2, y -> result.setValue(biFun.apply(data1.getValue(), y)));

        return result;
    }


    /**
     * This method provides a LiveData object which observes 2 different Livedata types,
     * allowing you to observe multiple values with a single callback
     * <p>
     * It works as a {@link BiFunction Binary Function} version of the {@link androidx.lifecycle.Transformations Transformations} map method.
     * <p>
     * Will not trigger the resulting livedata unless both source livedatas contain non-null values.
     *
     * @param data1 First LiveData object
     * @param data2 Second LiveData object
     * @param biFun BiFunction to perform transformation
     * @param <X>   Type parameter of data1
     * @param <Y>   Type parameter of data2
     * @param <O>   Type parameter of returned LiveData object
     * @return LiveData with the value returned by biFun
     */

    @MainThread
    public static <X, Y, O> LiveData<O> biMapNullSafe(LiveData<X> data1, LiveData<Y> data2, final BiFunction<X, Y, O> biFun) {
        final MediatorLiveData<O> result = new MediatorLiveData<>();
        result.addSource(data1, x -> {
            if (x != null && data2.getValue() != null)
                result.setValue(biFun.apply(x, data2.getValue()));
        });
        result.addSource(data2, y -> {
            if (data1.getValue() != null && y != null)
                result.setValue(biFun.apply(data1.getValue(), y));
        });

        return result;
    }

    /**
     * This method provides a LiveData object which observes 2 different Livedata types,
     * allowing you to observe multiple values with a single callback
     * <p>
     * It works as a {@link BiFunction Binary Function} version of the {@link androidx.lifecycle.Transformations Transformations} map method.
     * <p>
     * Will not trigger the resulting livedata unless both source livedatas contain non-null values.
     *
     * @param data1  First LiveData object
     * @param data2  Second LiveData object
     * @param data3  Third LiveData object
     * @param triFun TriFunction to perform transformation
     * @param <X>    Type parameter of data1
     * @param <Y>    Type parameter of data2
     * @param <Z>    Type parameter of data3
     * @param <O>    Type parameter of returned LiveData object
     * @return LiveData with the value returned by triFun
     */

    @MainThread
    public static <X, Y, Z, O> LiveData<O> triMapNullSafe(LiveData<X> data1, LiveData<Y> data2, LiveData<Z> data3, final TriFunction<X, Y, Z, O> triFun) {
        final MediatorLiveData<O> result = new MediatorLiveData<>();
        result.addSource(data1, x -> {
            if (x != null && data2.getValue() != null && data3.getValue() != null)
                result.setValue(triFun.apply(x, data2.getValue(), data3.getValue()));
        });
        result.addSource(data2, y -> {
            if (data1.getValue() != null && y != null && data3.getValue() != null)
                result.setValue(triFun.apply(data1.getValue(), y, data3.getValue()));
        });
        result.addSource(data3, z -> {
            if (data1.getValue() != null && data2.getValue() != null && z != null)
                result.setValue(triFun.apply(data1.getValue(), data2.getValue(), z));
        });

        return result;
    }
}


