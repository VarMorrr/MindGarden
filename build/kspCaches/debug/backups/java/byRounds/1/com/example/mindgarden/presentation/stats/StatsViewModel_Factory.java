package com.example.mindgarden.presentation.stats;

import com.example.mindgarden.domain.usecase.GetFlowerStatsUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class StatsViewModel_Factory implements Factory<StatsViewModel> {
  private final Provider<GetFlowerStatsUseCase> getFlowerStatsUseCaseProvider;

  public StatsViewModel_Factory(Provider<GetFlowerStatsUseCase> getFlowerStatsUseCaseProvider) {
    this.getFlowerStatsUseCaseProvider = getFlowerStatsUseCaseProvider;
  }

  @Override
  public StatsViewModel get() {
    return newInstance(getFlowerStatsUseCaseProvider.get());
  }

  public static StatsViewModel_Factory create(
      javax.inject.Provider<GetFlowerStatsUseCase> getFlowerStatsUseCaseProvider) {
    return new StatsViewModel_Factory(Providers.asDaggerProvider(getFlowerStatsUseCaseProvider));
  }

  public static StatsViewModel_Factory create(
      Provider<GetFlowerStatsUseCase> getFlowerStatsUseCaseProvider) {
    return new StatsViewModel_Factory(getFlowerStatsUseCaseProvider);
  }

  public static StatsViewModel newInstance(GetFlowerStatsUseCase getFlowerStatsUseCase) {
    return new StatsViewModel(getFlowerStatsUseCase);
  }
}
