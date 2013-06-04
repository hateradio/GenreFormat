package com.github.hateradio.GenreFormat

import org.apache.commons.lang3.text.WordUtils

/** Genre normalizer
 * 
 * This is the heart of the application. Maintains common fixes and the format method.
 */
object Genre {

  /** Map of common corrections */
  private val corrections = Map(
    "acappella" -> "A Capella",
    "acapella" -> "A Capella",
    "acapela" -> "A Capella",
    "alt" -> "Alternative",
    "avantgarde" -> "Avant-Garde",
    "edm" -> "EDM",
    "electro" -> "Electronic",
    "electronica" -> "Electronic",
    "folkrock" -> "Folk-Rock",
    "hiphop" -> "Hip-Hop",
    "idm" -> "IDM",
    "postrock" -> "Post-Rock",
    "psychadelic" -> "Psychedelic",
    "synth" -> "",
    "synthpop" -> ""
  )

  /** Fixes a string if matched in the corrections map
   *
   * Replaces white space and converts a string to lower case to find a match
   *
   * @param genre the word to fix
   * @return a correct genre
   */
  private def fix(genre: String): String = corrections.getOrElse(genre.replaceAll("\\s+", "").toLowerCase, genre)

  /** Formats a string of genres
   *
   * Strips and replaces unnecessary text, converts "and" to "&", capitalizes and fixes words.
   *
   * @param s the string of genres to format
   * @return a formatted string of unique genres separated by semi-colons
   *
   */
  def format(s: String): String = {
    s.replaceAll("Genre ?:? ?(?:.+?multiple values.+? )?", "")
      .replaceAll("[<>?|:_.\"]", " ")
      .replaceAll("(?i)\\band\\b", "&")
      .replaceAll("\\s+", " ")
      .split("[/\\\\;,|]")
      .map(s => fix(WordUtils.capitalizeFully(s.trim, '-', ' ', '&')))
      .filter(_.length > 0)
      .distinct
      .mkString("; ")
  }
}