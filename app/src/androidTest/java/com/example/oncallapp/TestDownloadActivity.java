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


import android.text.format.DateUtils;
import android.util.Log;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.IdlingPolicies;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;




import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;



import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestDownloadActivity {
    private static final String TAG = TestDownloadActivity.class.getSimpleName();

    @Rule
    public ActivityTestRule<DownloadActivity> mActivityTestRule = new ActivityTestRule<>(DownloadActivity.class);

    /**
     * Download the first file found in the S3 bucket
     */


    @Test
    public void downloadActivityTest() {
        // Set the Idling Resource timeout to 1 second
        long waitingTime = DateUtils.SECOND_IN_MILLIS;
        Log.d(TAG,"setIdlingResourceTimeout");

        IdlingPolicies.setIdlingResourceTimeout(waitingTime, TimeUnit.MILLISECONDS);
        DownloadCompleteIdlingResource downloadCompleteIdlingResource = new DownloadCompleteIdlingResource();

        // Perform the test steps
        ViewInteraction button = onView(withId(R.id.buttonDownload));
        Log.d(TAG,"click DownloadMain button");
        button.perform(click());

        DataInteraction linearLayout = onData(anything())
                .inAdapterView(withId(android.R.id.list))
                .atPosition(0);
        Log.d(TAG,"Select downloading file");
        linearLayout.perform(click());

        ViewInteraction textView = onView(withId(R.id.textState));
        IdlingRegistry.getInstance().register(downloadCompleteIdlingResource);
        Log.d(TAG,"textView.check COMPLETED");

        Log.d(TAG,"unregister(downloadCompleteIdlingResource)");
        IdlingRegistry.getInstance().unregister(downloadCompleteIdlingResource);
        Log.d(TAG,"finished");
    }
}