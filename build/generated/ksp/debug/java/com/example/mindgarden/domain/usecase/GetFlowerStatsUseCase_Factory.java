package com.example.mindgarden.domain.usecase;

import com.example.mindgarden.domain.repository.FlowerRepository;
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
public final class GetFlowerStatsUseCase_Factory implements Factory<GetFlowerStatsUseCase> {
  private final Provider<FlowerRepository> repositoryProvider;

  public GetFlowerStatsUseCase_Factory(Provider<FlowerRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetFlowerStatsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetFlowerStatsUseCase_Factory create(
      javax.inject.Provider<FlowerRepository> repositoryProvider) {
    return new GetFlowerStatsUseCase_Factory(Providers.asDaggerProvider(repositoryProvider));
  }

  public static GetFlowerStatsUseCase_Factory create(
      Provider<FlowerRepository> repositoryProvider) {
    return new GetFlowerStatsUseCase_Factory(repositoryProvider);
  }

  public static GetFlowerStatsUseCase newInstance(FlowerRepository repository) {
    return new GetFlowerStatsUseCase(repository);
  }
}
