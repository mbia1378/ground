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

package com.google.android.gnd;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import com.google.android.gnd.repository.DataRepository;
import com.google.android.gnd.ui.common.Navigator;
import com.google.android.gnd.ui.common.SharedViewModel;
import javax.inject.Inject;

@SharedViewModel
public class MainViewModel extends ViewModel {

  private final DataRepository dataRepository;
  private final Navigator navigator;
  private MutableLiveData<WindowInsetsCompat> windowInsetsLiveData;

  @Inject
  public MainViewModel(DataRepository dataRepository, Navigator navigator) {
    windowInsetsLiveData = new MutableLiveData<>();
    this.dataRepository = dataRepository;
    this.navigator = navigator;
  }

  public LiveData<WindowInsetsCompat> getWindowInsets() {
    return windowInsetsLiveData;
  }

  WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets) {
    windowInsetsLiveData.setValue(insets);
    return insets;
  }

  public void onSignedOut() {
    dataRepository.clearActiveProject();
    navigator.showSignInScreen();
  }

  public void onSignedIn() {
    navigator.showHomeScreen();
  }
}
