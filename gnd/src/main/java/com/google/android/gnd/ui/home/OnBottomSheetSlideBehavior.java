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

package com.google.android.gnd.ui.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.gnd.R;
import com.google.android.gnd.ui.common.BottomSheetBehavior;

public abstract class OnBottomSheetSlideBehavior<V extends View>
    extends CoordinatorLayout.Behavior<V> {
  public OnBottomSheetSlideBehavior(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  protected abstract void onSheetScrolled(
      CoordinatorLayout parent, V child, SheetSlideMetrics metrics);

  @Override
  public boolean layoutDependsOn(CoordinatorLayout parent, V child, View dependency) {
    return dependency.getId() == R.id.bottom_sheet_scroll_view;
  }

  @Override
  public boolean onDependentViewChanged(CoordinatorLayout parent, V child, View dependency) {
    onSheetScrolled(parent, child, new SheetSlideMetrics(parent, dependency));
    return false;
  }

  public static class SheetSlideMetrics {
    private final CoordinatorLayout parent;
    private final View view;

    public SheetSlideMetrics(CoordinatorLayout parent, View view) {
      this.parent = parent;
      this.view = view;
    }

    public static float scale(
        float value, float before1, float before2, float after1, float after2) {
      if (before1 > before2) {
        return scale(value, before2, before1, after1, after2);
      }
      float minBefore = before1;
      float maxBefore = before2;
      float rangeBefore = maxBefore - minBefore;
      float minAfter = Math.min(after1, after2);
      float maxAfter = Math.max(after1, after2);
      float rangeAfter = maxAfter - minAfter;
      float ratio = (value - minBefore) / rangeBefore;
      if (ratio > 1) {
        ratio = 1;
      }
      if (ratio < 0) {
        ratio = 0;
      }
      if (after1 > after2) {
        ratio = 1 - ratio;
      }
      return ratio * rangeAfter + minAfter;
    }

    public float getVisibleRatio() {
      return 1 - ((float) view.getTop()) / ((float) parent.getHeight());
    }

    public void showWithSheet(Drawable view, float hideThreshold, float showThreshold) {
      view.setAlpha((int) scale(getVisibleRatio(), hideThreshold, showThreshold, 0.0f, 255.0f));
    }

    public void hideWithSheet(Drawable view, float showThreshold, float hideThreshold) {
      view.setAlpha((int) scale(getVisibleRatio(), showThreshold, hideThreshold, 255.0f, 0.0f));
    }

    public void showWithSheet(@Nullable View view, float hideThreshold, float showThreshold) {
      if (view != null) {
        view.setAlpha(scale(getVisibleRatio(), hideThreshold, showThreshold, 0.0f, 1.0f));
      }
    }

    public void hideWithSheet(@Nullable View view, float showThreshold, float hideThreshold) {
      if (view != null) {
        view.setAlpha(scale(getVisibleRatio(), showThreshold, hideThreshold, 1.0f, 0.0f));
      }
    }

    public int getToolbarHeight() {
      return parent.findViewById(R.id.toolbar).getHeight();
    }

    public int getVisibleHeight() {
      return Math.max(parent.getHeight() - view.getTop(), 0);
    }

    public int getTop() {
      return view.getTop();
    }

    public int getTabLayoutTop() {
      return view.getTop() + view.getPaddingTop();
    }

    public int getPeekHeight() {
      return BottomSheetBehavior.from(view).getPeekHeight();
    }
  }
}
