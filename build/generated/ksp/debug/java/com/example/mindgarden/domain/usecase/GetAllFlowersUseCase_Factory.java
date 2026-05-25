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
public final class GetAllFlowersUseCase_Factory implements Factory<GetAllFlowersUseCase> {
  private final Provider<FlowerRepository> repositoryProvider;

  public GetAllFlowersUseCase_Factory(Provider<FlowerRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetAllFlowersUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetAllFlowersUseCase_Factory create(
      javax.inject.Provider<FlowerRepository> repositoryProvider) {
    return new GetAllFlowersUseCase_Factory(Providers.asDaggerProvider(repositoryProvider));
  }

  public static GetAllFlowersUseCase_Factory create(Provider<FlowerRepository> repositoryProvider) {
    return new GetAllFlowersUseCase_Factory(repositoryProvider);
  }

  public static GetAllFlowersUseCase newInstance(FlowerRepository repository) {
    return new GetAllFlowersUseCase(repository);
  }
}
