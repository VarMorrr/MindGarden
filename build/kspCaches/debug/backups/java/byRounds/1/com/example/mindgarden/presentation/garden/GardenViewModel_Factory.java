package com.example.mindgarden.presentation.garden;

import com.example.mindgarden.domain.usecase.GetAllFlowersUseCase;
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
public final class GardenViewModel_Factory implements Factory<GardenViewModel> {
  private final Provider<GetAllFlowersUseCase> getAllFlowersUseCaseProvider;

  public GardenViewModel_Factory(Provider<GetAllFlowersUseCase> getAllFlowersUseCaseProvider) {
    this.getAllFlowersUseCaseProvider = getAllFlowersUseCaseProvider;
  }

  @Override
  public GardenViewModel get() {
    return newInstance(getAllFlowersUseCaseProvider.get());
  }

  public static GardenViewModel_Factory create(
      javax.inject.Provider<GetAllFlowersUseCase> getAllFlowersUseCaseProvider) {
    return new GardenViewModel_Factory(Providers.asDaggerProvider(getAllFlowersUseCaseProvider));
  }

  public static GardenViewModel_Factory create(
      Provider<GetAllFlowersUseCase> getAllFlowersUseCaseProvider) {
    return new GardenViewModel_Factory(getAllFlowersUseCaseProvider);
  }

  public static GardenViewModel newInstance(GetAllFlowersUseCase getAllFlowersUseCase) {
    return new GardenViewModel(getAllFlowersUseCase);
  }
}
