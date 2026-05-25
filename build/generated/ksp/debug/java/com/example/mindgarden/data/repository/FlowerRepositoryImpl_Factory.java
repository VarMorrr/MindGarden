package com.example.mindgarden.data.repository;

import com.example.mindgarden.data.local.FlowerDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class FlowerRepositoryImpl_Factory implements Factory<FlowerRepositoryImpl> {
  private final Provider<FlowerDao> daoProvider;

  public FlowerRepositoryImpl_Factory(Provider<FlowerDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public FlowerRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static FlowerRepositoryImpl_Factory create(javax.inject.Provider<FlowerDao> daoProvider) {
    return new FlowerRepositoryImpl_Factory(Providers.asDaggerProvider(daoProvider));
  }

  public static FlowerRepositoryImpl_Factory create(Provider<FlowerDao> daoProvider) {
    return new FlowerRepositoryImpl_Factory(daoProvider);
  }

  public static FlowerRepositoryImpl newInstance(FlowerDao dao) {
    return new FlowerRepositoryImpl(dao);
  }
}
