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

package com.google.android.gnd.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.util.Log;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gnd.R;
import com.google.android.gnd.ui.util.ViewUtil;

// TODO: Move to common, rename Marker.
public class MapIcon {
  private static final String TAG = MapIcon.class.getSimpleName();
  private final Context context;
  private BitmapDrawable drawable;
  private int color;

  public MapIcon(Context context, @Nullable String iconId, @Nullable String color) {
    this.context = context;
    this.color = getIconColor(context, color);
    int resourceId = getResourceId(context, iconId);
    this.drawable = (BitmapDrawable) ContextCompat.getDrawable(context, resourceId);
  }

  private static int getIconColor(Context context, String color) {
    try {
      return Color.parseColor(color);
    } catch (Exception e) {
      return context.getResources().getColor(R.color.colorMapAccent);
    }
  }

  @NonNull
  public static @DrawableRes int getResourceId(Context context, @Nullable String iconId) {
    if (iconId == null) {
      return R.drawable.ic_default_map_marker;
    }
    try {
      String resourceName = "ic_marker_" + iconId.replace("-", "_");
      int resourceId =
          context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
      if (resourceId > 0) {
        return resourceId;
      }
    } catch (Resources.NotFoundException e) {
      Log.w(TAG, e);
    }
    return R.drawable.ic_default_map_marker;
  }

  // TODO: Cache tinted bitmaps.
  public BitmapDescriptor getBitmap() {
    Bitmap tintedBitmap = ViewUtil.tintBitmap(drawable.getBitmap(), color);
    // TODO: Figure out consistent way to scale images.
    return BitmapDescriptorFactory.fromBitmap(tintedBitmap);
  }

  // TODO: Cache tinted bitmaps.
  public BitmapDescriptor getGreyBitmap() {
    Bitmap tintedBitmap =
        ViewUtil.tintBitmap(
            drawable.getBitmap(), context.getResources().getColor(R.color.colorGrey300));
    return BitmapDescriptorFactory.fromBitmap(tintedBitmap);
  }

  // TODO: Cache tinted bitmaps.
  public BitmapDescriptor getWhiteBitmap() {
    Bitmap tintedBitmap =
        ViewUtil.tintBitmap(
            drawable.getBitmap(), context.getResources().getColor(R.color.colorBackground));
    return BitmapDescriptorFactory.fromBitmap(tintedBitmap);
  }
}
