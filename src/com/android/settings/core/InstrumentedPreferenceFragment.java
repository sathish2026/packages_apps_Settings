/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.core;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.settings.core.instrumentation.Instrumentable;
import com.android.settings.core.instrumentation.MetricsFeatureProvider;
import com.android.settings.core.instrumentation.VisibilityLoggerMixin;
import com.android.settings.core.lifecycle.ObservablePreferenceFragment;
import com.android.settings.overlay.FeatureFactory;
import com.android.settings.widget.PreferenceDividerDecoration;
import com.android.settings.survey.SurveyMixin;

/**
 * Instrumented fragment that logs visibility state.
 */
public abstract class InstrumentedPreferenceFragment extends ObservablePreferenceFragment
        implements Instrumentable {

    protected MetricsFeatureProvider mMetricsFeatureProvider;

    // metrics placeholder value. Only use this for development.
    protected final int PLACEHOLDER_METRIC = 10000;
    protected final int SYSTEM_CATEGORY_FRAGMENT = PLACEHOLDER_METRIC + 1;
    protected final int STORAGE_CATEGORY_FRAGMENT = PLACEHOLDER_METRIC + 2;
    protected final int NETWORK_CATEGORY_FRAGMENT = PLACEHOLDER_METRIC + 3;
    protected final int CONNECTED_DEVICE_CATEGORY_FRAGMENT = PLACEHOLDER_METRIC + 4;
    protected final int APP_AND_NOTIFICATION_CATEGORY_FRAGMENT = PLACEHOLDER_METRIC + 5;
    protected final int INPUT_AND_GESTURE_CATEGORY_FRAGMENT = PLACEHOLDER_METRIC + 6;
    protected final int LANGUAGE_AND_REGION_CATEGORY_FRAGMENT = PLACEHOLDER_METRIC + 7;
    protected final int GESTURE_SWIPE_TO_NOTIFICATION = PLACEHOLDER_METRIC + 8;
    protected final int GESTURE_DOUBLE_TAP_POWER = PLACEHOLDER_METRIC + 9;
    protected final int GESTURE_PICKUP = PLACEHOLDER_METRIC + 10;
    protected final int GESTURE_DOUBLE_TAP_SCREEN = PLACEHOLDER_METRIC + 11;
    protected final int GESTURE_DOUBLE_TWIST = PLACEHOLDER_METRIC + 12;

    private final PreferenceDividerDecoration mDividerDecoration =
            new PreferenceDividerDecoration();

    public InstrumentedPreferenceFragment() {
        // Mixin that logs visibility change for activity.
        getLifecycle().addObserver(new VisibilityLoggerMixin(getMetricsCategory()));
        getLifecycle().addObserver(new SurveyMixin(this, getClass().getSimpleName()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMetricsFeatureProvider = FeatureFactory.getFactory(context).getMetricsFeatureProvider();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View view = super.onCreateView(inflater, container, savedInstanceState);
        getListView().addItemDecoration(mDividerDecoration);
        return view;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    }

    @Override
    public void setDivider(Drawable divider) {
        mDividerDecoration.setDivider(divider);
        super.setDivider(new ColorDrawable(Color.TRANSPARENT));
    }
}