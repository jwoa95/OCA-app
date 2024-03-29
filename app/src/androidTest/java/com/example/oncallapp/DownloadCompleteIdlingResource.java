package com.example.oncallapp;
/*
        * Copyright 2015-2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.
        *
        * Licensed under the Apache License, Version 2.0 (the "License").
        * You may not use this file except in compliance with the License.
        * A copy of the License is located at
        *
        *  http://aws.amazon.com/apache2.0
        *
        * or in the "license" file accompanying this file. This file is distributed
        * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
        * express or implied. See the License for the specific language governing
        * permissions and limitations under the License.
        */

import android.app.Activity;
import android.widget.TextView;


import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.core.internal.deps.guava.collect.Iterables;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

public final class DownloadCompleteIdlingResource implements IdlingResource {
    private IdlingResource.ResourceCallback resourceCallback;
    private boolean isIdle;

    @Override
    public String getName() {
        return DownloadCompleteIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        if (isIdle) return true;

        Activity activity = getCurrentActivity();
        TextView stateView = activity.findViewById(R.id.textState);
        isIdle = (stateView != null && stateView.getText().equals("COMPLETED"));
        if (isIdle) {
            resourceCallback.onTransitionToIdle();
        }
        return isIdle;
    }

    private Activity getCurrentActivity() {
        java.util.Collection<Activity> activities = ActivityLifecycleMonitorRegistry.getInstance()
                .getActivitiesInStage(Stage.RESUMED);
        return Iterables.getOnlyElement(activities);
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }
}