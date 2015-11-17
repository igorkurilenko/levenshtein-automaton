package io.itdraft.levenshteinautomaton.description.parametric

import io.itdraft.levenshteinautomaton.AutomatonConfigWithWithEncodedParametricDescription
import io.itdraft.levenshteinautomaton.description._
import io.itdraft.levenshteinautomaton.description.parametric.coding.ParametricStateCodec

protected[levenshteinautomaton]
class ParametricState(state: Int,
                      val automatonConfig: AutomatonConfigWithWithEncodedParametricDescription)
  extends State {

  private lazy val w = automatonConfig.w
  private lazy val n = automatonConfig.n

  private lazy val parametricDescription = automatonConfig.encodedParametricDescription
  private lazy val statesCount = parametricDescription.getStatesCount

  lazy val relevantSubwordMaxLength = Math.min(2 * n + 1, w - minBoundary)

  def minBoundary = ParametricStateCodec.getMinBoundary(state, statesCount)

  lazy val isFinal = ParametricStateCodec.isFinal(state,
    w, n, automatonConfig.encodedParametricDescription)


  lazy val isFailure = ParametricStateCodec.isFailure(state, statesCount)

  def transit(xCodePoint: Int) = {
    val nextState = ParametricStateCodec.transit(xCodePoint, state,
      w, n, automatonConfig.word, parametricDescription)

    new ParametricState(nextState, automatonConfig)
  }
}
