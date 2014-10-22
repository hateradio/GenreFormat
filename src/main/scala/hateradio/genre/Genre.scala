package hateradio.genre

import scala.language.postfixOps
import org.apache.commons.lang3.text.WordUtils

/** Genre normalizer
 * 
 * This is the heart of the application. Maintains common fixes and the format method.
 */
object Genre {

  /** Map of post-processed corrections */
  private val postCorrections = Map(
    "edm" -> "EDM",
    "idm" -> "IDM"
  )

  /** Common misspellings and undesirable genres, along with corrections */
  private val corrections = Map(
    "acapela|acapella|acappella" -> "A Capella",
    "alt" -> "Alternative",
    "and" -> "&",
    "avant.?garde" -> "Avant-Garde",
    "electronica|electro" -> "Electronic",
    "folk.?rock" -> "Folk-Rock",
    "hip.?hop" -> "Hip-Hop",
    "indie" -> "",
    "post.?rock" -> "Post-Rock",
    "psychadelic" -> "Psychedelic",
    "synth" -> "",
    "prog" -> "progressive"
  ) map { t => (s"\\b${t._1}\\b", t._2) }

  private val misc = Map(
    "genre ?:? ?(?:.+?multiple values.+? )?" -> "",
    "[<>?|:_.\"]" -> " ",
    "\\s+" -> " "
  )

  /** Fixes a string if matched in the corrections map
   *
   * Replaces white space and converts a string to lower case to find a match
   *
   * @param genre the word to fix
   * @return a correct genre
   */
  private def postFix(genre: String): String = postCorrections getOrElse (genre.trim.toLowerCase, genre)

  /** Formats a string of genres
   *
   * Strips and replaces unnecessary text, converts "and" to "&", capitalizes and fixes words.
   *
   * @param input the string of genres to format
   * @return a formatted string of unique genres separated by semi-colons
   */
  def format(input: String): String = input match {
    case null => ""
    case "" => input
    case s: String => cleaner(s)
  }

  private def cleaner(input: String): String = {
    val clean = (misc ++ corrections).foldLeft(input toLowerCase) { (str, t) => str replaceAll (t._1, t._2) }

    val genres = clean split "[/\\\\;,|]"

    genres
      .map(g => postFix(WordUtils.capitalizeFully(g.trim, '-', ' ', '&')))
      .filter(_.length > 0)
      .distinct
      .mkString("; ")
  }

}
