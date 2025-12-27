package com.example.animeapp.data.repository

import com.example.animeapp.data.api.JikanApiService
import com.example.animeapp.data.db.AnimeDao
import com.example.animeapp.data.model.Anime
import com.example.animeapp.data.model.AnimeCharacter
import com.example.animeapp.data.model.toDomain
import com.example.animeapp.data.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import java.io.IOException

@Singleton
class AnimeRepository @Inject constructor(
    private val api: JikanApiService,
    private val dao: AnimeDao
) {

    val animeList: Flow<List<Anime>> = dao.getAllAnime().map { entities ->
        entities.map { it.toDomain() }
    }

    suspend fun refreshAnimeList(page: Int = 1): Result<Unit> {
        return try {
            val response = api.getTopAnime(page)
            if (response.isSuccessful) {
                val animeDtos = response.body()?.data ?: emptyList()
                if (page == 1) {
                    dao.deleteAll()
                }
                dao.insertAll(animeDtos.map { it.toEntity() })
                Result.success(Unit)
            } else {
                Result.failure(IOException("API Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchAnime(query: String): Result<List<Anime>> {
        return try {
            val response = api.searchAnime(query)
            if (response.isSuccessful) {
                val animeDtos = response.body()?.data ?: emptyList()
                val domainList = animeDtos.map { it.toDomain() }
                Result.success(domainList)
            } else {
                Result.failure(IOException("API Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAnimeDetail(id: Int): Result<Anime> {
        try {
            val detailResponse = api.getAnimeDetails(id)
            val charactersResponse = api.getAnimeCharacters(id)

            if (detailResponse.isSuccessful) {
                val dto = detailResponse.body()?.data
                if (dto != null) {
                    dao.insert(dto.toEntity())
                    
                    var domainAnime = dto.toDomain()
                    
                    if (charactersResponse.isSuccessful) {
                        val chars = charactersResponse.body()?.data?.map { 
                            AnimeCharacter(
                                name = it.character.name,
                                role = it.role,
                                imageUrl = it.character.images?.jpg?.imageUrl
                            )
                        } ?: emptyList()
                        domainAnime = domainAnime.copy(cast = chars)
                    }
                    
                    return Result.success(domainAnime)
                }
            }
        } catch (e: Exception) {
        }

        val localEntity = dao.getAnimeById(id)
        return if (localEntity != null) {
            Result.success(localEntity.toDomain())
        } else {
            Result.failure(IOException("Anime not found locally and network failed"))
        }
    }
}
