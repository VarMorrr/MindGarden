package com.example.mindgarden.di;

import com.example.mindgarden.data.local.AppDatabase;
import com.example.mindgarden.data.local.FlowerDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.Providers;
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
public final class DatabaseModule_ProvideFlowerDaoFactory implements Factory<FlowerDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideFlowerDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public FlowerDao get() {
    return provideFlowerDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideFlowerDaoFactory create(
      javax.inject.Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideFlowerDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideFlowerDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideFlowerDaoFactory(dbProvider);
  }

  public static FlowerDao provideFlowerDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideFlowerDao(db));
  }
}
