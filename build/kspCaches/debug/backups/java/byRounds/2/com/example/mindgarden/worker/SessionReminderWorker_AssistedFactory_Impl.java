package com.example.mindgarden.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class SessionReminderWorker_AssistedFactory_Impl implements SessionReminderWorker_AssistedFactory {
  private final SessionReminderWorker_Factory delegateFactory;

  SessionReminderWorker_AssistedFactory_Impl(SessionReminderWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public SessionReminderWorker create(Context p0, WorkerParameters p1) {
    return delegateFactory.get(p0, p1);
  }

  public static Provider<SessionReminderWorker_AssistedFactory> create(
      SessionReminderWorker_Factory delegateFactory) {
    return InstanceFactory.create(new SessionReminderWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<SessionReminderWorker_AssistedFactory> createFactoryProvider(
      SessionReminderWorker_Factory delegateFactory) {
    return InstanceFactory.create(new SessionReminderWorker_AssistedFactory_Impl(delegateFactory));
  }
}
