package io.itdraft.levenshteinautomaton

import io.itdraft.levenshteinautomaton.description.parametric.coding.EncodedParametricDscr

protected[levenshteinautomaton]
sealed trait AutomatonConfig {
  lazy val w = word.codePointsCount
  lazy val n = degree

  def word: String

  def degree: Int
}


protected[levenshteinautomaton]
case class DefaultAutomatonConfig(word: String,
                                  degree: Int,
                                  inclTranspositions: Boolean = false)
  extends AutomatonConfig


protected[levenshteinautomaton]
case class AutomatonConfigWithWithEncParametricDescr(word: String,
                                                    degree: Int,
                                                    encodedParametricDscr: EncodedParametricDscr)
  extends AutomatonConfig


protected[levenshteinautomaton]
object AutomatonConfig {
  def apply(word: String, automatonDegree: Int, inclTranspositions: Boolean) =
    Option(EncodedParametricDscr.get(automatonDegree, inclTranspositions)) match {
      case Some(parametricDescription) =>
        AutomatonConfigWithWithEncParametricDescr(word, automatonDegree, parametricDescription)
      case None => DefaultAutomatonConfig(word, automatonDegree, inclTranspositions)
    }
}