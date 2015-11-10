package io.itdraft.levenshteinautomaton.description.parametric

import io.itdraft.levenshteinautomaton.AutomatonConfigWithWithEncParametricDescr
import io.itdraft.levenshteinautomaton.description._
import io.itdraft.levenshteinautomaton.description.parametric.coding.ParametricStateCodec

protected[levenshteinautomaton]
class ParametricState(state: Int,
                      val automatonConfig: AutomatonConfigWithWithEncParametricDescr)
  extends State {

  private lazy val parametricDescription = automatonConfig.encodedParametricDscr
  private lazy val statesCount = parametricDescription.getStatesCount

  def minBoundary = ParametricStateCodec.getMinBoundary(state, statesCount)

  lazy val isFinal = ParametricStateCodec.isFinal(state,
    w, n, automatonConfig.encodedParametricDscr)


  lazy val isFailure = ParametricStateCodec.isFailure(state, statesCount)

  def transit(xCodePoint: Int) = {
    val nextState = ParametricStateCodec.transit(xCodePoint, state,
      w, n, automatonConfig.word, parametricDescription)

    new ParametricState(nextState, automatonConfig)
  }
}
