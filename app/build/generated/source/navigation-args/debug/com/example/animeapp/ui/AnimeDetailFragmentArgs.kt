package com.example.animeapp.ui

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Int
import kotlin.jvm.JvmStatic

public data class AnimeDetailFragmentArgs(
  public val animeId: Int,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putInt("animeId", this.animeId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("animeId", this.animeId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): AnimeDetailFragmentArgs {
      bundle.setClassLoader(AnimeDetailFragmentArgs::class.java.classLoader)
      val __animeId : Int
      if (bundle.containsKey("animeId")) {
        __animeId = bundle.getInt("animeId")
      } else {
        throw IllegalArgumentException("Required argument \"animeId\" is missing and does not have an android:defaultValue")
      }
      return AnimeDetailFragmentArgs(__animeId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): AnimeDetailFragmentArgs {
      val __animeId : Int?
      if (savedStateHandle.contains("animeId")) {
        __animeId = savedStateHandle["animeId"]
        if (__animeId == null) {
          throw IllegalArgumentException("Argument \"animeId\" of type integer does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"animeId\" is missing and does not have an android:defaultValue")
      }
      return AnimeDetailFragmentArgs(__animeId)
    }
  }
}
