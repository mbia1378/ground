/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.gnd.ui.startup;

import static com.google.android.gnd.rx.RxAutoDispose.autoDisposable;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gnd.R;
import com.google.android.gnd.system.AuthenticationManager;
import com.google.android.gnd.system.GoogleApiManager;
import com.google.android.gnd.ui.common.AbstractFragment;
import com.google.android.gnd.ui.common.EphemeralPopups;
import javax.inject.Inject;

public class StartupFragment extends AbstractFragment {
  private static final String TAG = StartupFragment.class.getSimpleName();

  @Inject GoogleApiManager googleApiManager;
  @Inject AuthenticationManager authenticationManager;

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.startup_frag, container, false);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);

    googleApiManager
        .installGooglePlayServices()
        .as(autoDisposable(getActivity()))
        .subscribe(this::onGooglePlayServicesReady, this::onGooglePlayServicesFailed);
  }

  private void onGooglePlayServicesReady() {
    authenticationManager.init();
  }

  private void onGooglePlayServicesFailed(Throwable t) {
    Log.e(TAG, "Google Play Services install failed", t);

    EphemeralPopups.showError(getActivity(), R.string.google_api_install_failed);

    getActivity().finish();
  }
}
