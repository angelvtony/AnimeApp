package com.example.animeapp.data.repository;

import com.example.animeapp.data.api.JikanApiService;
import com.example.animeapp.data.db.AnimeDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
    "KotlinInternalInJava"
})
public final class AnimeRepository_Factory implements Factory<AnimeRepository> {
  private final Provider<JikanApiService> apiProvider;

  private final Provider<AnimeDao> daoProvider;

  public AnimeRepository_Factory(Provider<JikanApiService> apiProvider,
      Provider<AnimeDao> daoProvider) {
    this.apiProvider = apiProvider;
    this.daoProvider = daoProvider;
  }

  @Override
  public AnimeRepository get() {
    return newInstance(apiProvider.get(), daoProvider.get());
  }

  public static AnimeRepository_Factory create(Provider<JikanApiService> apiProvider,
      Provider<AnimeDao> daoProvider) {
    return new AnimeRepository_Factory(apiProvider, daoProvider);
  }

  public static AnimeRepository newInstance(JikanApiService api, AnimeDao dao) {
    return new AnimeRepository(api, dao);
  }
}
