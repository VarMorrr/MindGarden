package com.example.mindgarden.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class SessionReminderWorker_Factory {
  public SessionReminderWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params);
  }

  public static SessionReminderWorker_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SessionReminderWorker newInstance(Context context, WorkerParameters params) {
    return new SessionReminderWorker(context, params);
  }

  private static final class InstanceHolder {
    static final SessionReminderWorker_Factory INSTANCE = new SessionReminderWorker_Factory();
  }
}
