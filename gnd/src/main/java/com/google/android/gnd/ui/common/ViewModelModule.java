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

package com.google.android.gnd.ui.common;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.gnd.MainViewModel;
import com.google.android.gnd.ui.editrecord.EditRecordViewModel;
import com.google.android.gnd.ui.home.HomeScreenViewModel;
import com.google.android.gnd.ui.home.featuresheet.FeatureSheetViewModel;
import com.google.android.gnd.ui.home.featuresheet.RecordListViewModel;
import com.google.android.gnd.ui.home.mapcontainer.MapContainerViewModel;
import com.google.android.gnd.ui.projectselector.ProjectSelectorViewModel;
import com.google.android.gnd.ui.recorddetails.RecordDetailsViewModel;
import com.google.android.gnd.ui.signin.SignInViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
  @Binds
  @IntoMap
  @ViewModelKey(MapContainerViewModel.class)
  abstract ViewModel bindMapContainerViewModel(MapContainerViewModel viewModel);

  @Binds
  @IntoMap
  @ViewModelKey(MainViewModel.class)
  abstract ViewModel bindMainViewModel(MainViewModel viewModel);

  @Binds
  @IntoMap
  @ViewModelKey(SignInViewModel.class)
  abstract ViewModel bindSignInVideModel(SignInViewModel viewModel);

  @Binds
  @IntoMap
  @ViewModelKey(HomeScreenViewModel.class)
  abstract ViewModel bindHomeScreenViewModel(HomeScreenViewModel viewModel);

  @Binds
  @IntoMap
  @ViewModelKey(FeatureSheetViewModel.class)
  abstract ViewModel bindFeatureSheetViewModel(FeatureSheetViewModel viewModel);

  @Binds
  @IntoMap
  @ViewModelKey(ProjectSelectorViewModel.class)
  abstract ViewModel bindProjectSelectorViewModel(ProjectSelectorViewModel viewModel);

  @Binds
  @IntoMap
  @ViewModelKey(RecordListViewModel.class)
  abstract ViewModel bindRecordListViewModel(RecordListViewModel viewModel);

  @Binds
  @IntoMap
  @ViewModelKey(RecordDetailsViewModel.class)
  abstract ViewModel bindRecordDetailsViewModel(RecordDetailsViewModel viewModel);

  @Binds
  @IntoMap
  @ViewModelKey(EditRecordViewModel.class)
  abstract ViewModel bindEditRecordViewModel(EditRecordViewModel viewModel);

  @Binds
  abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
