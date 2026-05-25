package com.example.mindgarden.presentation.timer;

import com.example.mindgarden.domain.usecase.SaveFlowerUseCase;
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
public final class TimerViewModel_Factory implements Factory<TimerViewModel> {
  private final Provider<SaveFlowerUseCase> saveFlowerUseCaseProvider;

  public TimerViewModel_Factory(Provider<SaveFlowerUseCase> saveFlowerUseCaseProvider) {
    this.saveFlowerUseCaseProvider = saveFlowerUseCaseProvider;
  }

  @Override
  public TimerViewModel get() {
    return newInstance(saveFlowerUseCaseProvider.get());
  }

  public static TimerViewModel_Factory create(
      javax.inject.Provider<SaveFlowerUseCase> saveFlowerUseCaseProvider) {
    return new TimerViewModel_Factory(Providers.asDaggerProvider(saveFlowerUseCaseProvider));
  }

  public static TimerViewModel_Factory create(
      Provider<SaveFlowerUseCase> saveFlowerUseCaseProvider) {
    return new TimerViewModel_Factory(saveFlowerUseCaseProvider);
  }

  public static TimerViewModel newInstance(SaveFlowerUseCase saveFlowerUseCase) {
    return new TimerViewModel(saveFlowerUseCase);
  }
}
