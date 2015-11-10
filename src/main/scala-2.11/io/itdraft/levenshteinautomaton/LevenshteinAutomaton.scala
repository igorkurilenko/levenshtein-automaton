package io.itdraft.levenshteinautomaton

import io.itdraft.levenshteinautomaton.description._

import scala.collection.mutable

object LevenshteinAutomaton {
  def apply(inputWord: String, degree: Int, includeTranspositions: Boolean): LevenshteinAutomaton =
    apply(AutomatonConfig(inputWord, degree, includeTranspositions))

  def apply(config: AutomatonConfig) =
    new LevenshteinAutomaton(config)
}

class LevenshteinAutomaton protected[levenshteinautomaton](config: AutomatonConfig) {
  private implicit val _ = config
  lazy val initialState = State.initial

  def next(s: State, x: Int) = s.transit(x)
}