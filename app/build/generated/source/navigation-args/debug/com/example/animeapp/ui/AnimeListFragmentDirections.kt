package com.example.animeapp.ui

import android.os.Bundle
import androidx.navigation.NavDirections
import com.example.animeapp.R
import kotlin.Int

public class AnimeListFragmentDirections private constructor() {
  private data class ActionAnimeListFragmentToAnimeDetailFragment(
    public val animeId: Int,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_animeListFragment_to_animeDetailFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putInt("animeId", this.animeId)
        return result
      }
  }

  public companion object {
    public fun actionAnimeListFragmentToAnimeDetailFragment(animeId: Int): NavDirections =
        ActionAnimeListFragmentToAnimeDetailFragment(animeId)
  }
}
