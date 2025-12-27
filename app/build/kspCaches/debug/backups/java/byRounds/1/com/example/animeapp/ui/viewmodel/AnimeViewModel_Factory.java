package com.example.animeapp.ui.viewmodel;

import com.example.animeapp.data.repository.AnimeRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
    "KotlinInternalInJava"
})
public final class AnimeViewModel_Factory implements Factory<AnimeViewModel> {
  private final Provider<AnimeRepository> repositoryProvider;

  public AnimeViewModel_Factory(Provider<AnimeRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public AnimeViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static AnimeViewModel_Factory create(Provider<AnimeRepository> repositoryProvider) {
    return new AnimeViewModel_Factory(repositoryProvider);
  }

  public static AnimeViewModel newInstance(AnimeRepository repository) {
    return new AnimeViewModel(repository);
  }
}
