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

package com.google.android.gnd.vo;

import com.google.android.gnd.vo.Record.Response;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import java.util.Date;
import java8.util.Optional;

@AutoValue
public abstract class FeatureUpdate {

  // TODO: Simplify and delete?
  public enum Operation {
    NO_CHANGE,
    CREATE,
    UPDATE,
    DELETE
  }

  public abstract Feature getFeature();

  public abstract Operation getOperation();

  public abstract Date getClientTimestamp();

  public abstract ImmutableList<RecordUpdate> getRecordUpdatesList();

  public static Builder newBuilder() {
    return new AutoValue_FeatureUpdate.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setFeature(Feature newFeature);

    public abstract Builder setOperation(Operation newOperation);

    public abstract Builder setClientTimestamp(Date newClientTimestamp);

    public abstract Builder setRecordUpdatesList(ImmutableList<RecordUpdate> newRecordUpdatesList);

    public abstract FeatureUpdate build();
  }

  @AutoValue
  public abstract static class RecordUpdate {
    public abstract Record getRecord();

    public abstract Operation getOperation();

    public abstract ImmutableList<ResponseUpdate> getResponseUpdates();

    public static Builder newBuilder() {
      return new AutoValue_FeatureUpdate_RecordUpdate.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
      public abstract Builder setRecord(Record newRecord);

      public abstract Builder setOperation(Operation newOperation);

      public abstract Builder setResponseUpdates(
          ImmutableList<ResponseUpdate> newResponseUpdatesList);

      public abstract RecordUpdate build();
    }

    @AutoValue
    public abstract static class ResponseUpdate {
      public abstract String getElementId();

      public abstract Optional<Response> getResponse();

      public abstract Operation getOperation();

      public static Builder newBuilder() {
        return new AutoValue_FeatureUpdate_RecordUpdate_ResponseUpdate.Builder()
            .setResponse(Optional.empty());
      }

      @AutoValue.Builder
      public abstract static class Builder {
        public abstract Builder setElementId(String newElementId);

        public abstract Builder setResponse(Optional<Response> newResponse);

        public abstract Builder setOperation(Operation newOperation);

        public abstract ResponseUpdate build();
      }
    }
  }
}
