package com.github.hateradio.GenreFormat

import org.apache.commons.lang3.text.WordUtils

object Genre {

  private val corrections = Map(
    "acappella" -> "A Capella",
    "acapella" -> "A Capella",
    "acapela" -> "A Capella",
    "avantgarde" -> "Avant-Garde",
    "avant garde" -> "Avant-Garde",
    "edm" -> "EDM",
    "electronica" -> "Electronic",
    "idm" -> "IDM",
    "postrock" -> "Post-Rock"
  )

  private def fix(s: String): String = corrections.getOrElse(s.replaceAll("\\s+", "").toLowerCase, s)

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