package com.example.animeapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class TopAnimeResponse(
    val data: List<AnimeDto>
)

data class AnimeDetailResponse(
    val data: AnimeDto
)

data class AnimeCharactersResponse(
    val data: List<CharacterDto>
)

data class AnimeDto(
    @SerializedName("mal_id") val malId: Int,
    val title: String,
    val episodes: Int?,
    val score: Double?,
    val synopsis: String?,
    val images: AnimeImages?,
    val trailer: AnimeTrailer?,
    val genres: List<GenreDto>?
)

data class AnimeImages(
    val jpg: JpgImage?
)

data class JpgImage(
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("large_image_url") val largeImageUrl: String?
)

data class AnimeTrailer(
    @SerializedName("youtube_id") val youtubeId: String?,
    val url: String?,
    @SerializedName("embed_url") val embedUrl: String?
)

data class GenreDto(
    val name: String
)

data class CharacterDto(
    val character: CharacterData,
    val role: String
)

data class CharacterData(
    val name: String,
    val images: AnimeImages?
)

@Entity(tableName = "anime")
data class AnimeEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val episodes: Int?,
    val score: Double?,
    val imageUrl: String?,
    val synopsis: String?,
    val trailerUrl: String?,
    val genres: String
)

data class Anime(
    val id: Int,
    val title: String,
    val episodes: Int?,
    val score: Double?,
    val imageUrl: String?,
    val synopsis: String?,
    val trailerUrl: String?,
    val genres: List<String>,
    val cast: List<AnimeCharacter> = emptyList()
)

data class AnimeCharacter(
    val name: String,
    val role: String,
    val imageUrl: String?
)

fun AnimeDto.toEntity(): AnimeEntity {
    return AnimeEntity(
        id = malId,
        title = title,
        episodes = episodes,
        score = score,
        imageUrl = images?.jpg?.imageUrl,
        synopsis = synopsis,
        trailerUrl = trailer?.embedUrl,
        genres = genres?.joinToString(",") { it.name } ?: ""
    )
}

fun AnimeEntity.toDomain(): Anime {
    return Anime(
        id = id,
        title = title,
        episodes = episodes,
        score = score,
        imageUrl = imageUrl,
        synopsis = synopsis,
        trailerUrl = trailerUrl,
        genres = if (genres.isNotEmpty()) genres.split(",") else emptyList()
    )
}

fun AnimeDto.toDomain(): Anime {
    return Anime(
        id = malId,
        title = title,
        episodes = episodes,
        score = score,
        imageUrl = images?.jpg?.imageUrl,
        synopsis = synopsis,
        trailerUrl = trailer?.embedUrl,
        genres = genres?.map { it.name } ?: emptyList()
    )
}
