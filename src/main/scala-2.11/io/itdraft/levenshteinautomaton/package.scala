package io.itdraft

package object levenshteinautomaton {
  implicit class StringExt(val s: String) {
    lazy val codePointsCount = Character.codePointCount(s, 0, s.length)
  }
}
