package io.itdraft.levenshteinautomaton.description

import io.itdraft.levenshteinautomaton.description.nonparametric._
import io.itdraft.levenshteinautomaton.description.parametric.ParametricState
import io.itdraft.levenshteinautomaton.{AutomatonConfig, AutomatonConfigWithWithEncParametricDescr, DefaultAutomatonConfig}

trait State {
  protected lazy val w = automatonConfig.w
  protected lazy val n = automatonConfig.n

  /**
   * Returns the max relevant subword length for this `State`.
   */
  lazy val relevantSubwordMaxLength = Math.min(2 * n + 1, w - minBoundary)

  def minBoundary: Int

  /**
   * Tests if it's a failure state or not.
   */
  def isFailure: Boolean

  /**
   * Tests if it's a final state or not.
   */
  def isFinal: Boolean

  protected[levenshteinautomaton]
  def automatonConfig: AutomatonConfig

  /**
   * Returns a new `State` produced by elementary transition of this `State`.
   * @param codePoint Symbol from the alphabet.
   */
  protected[levenshteinautomaton]
  def transit(codePoint: Int): State
}


object State {
  protected[levenshteinautomaton]
  def initial()(implicit ch: AutomatonConfig): State = ch match {
    case c: DefaultAutomatonConfig => initial(c)
    case c: AutomatonConfigWithWithEncParametricDescr => initial(c)
  }

  protected[levenshteinautomaton]
  def initial(ch: DefaultAutomatonConfig): NonParametricState = {
    implicit val _ = ch
    State(0 ^# 0)
  }

  protected[levenshteinautomaton]
  def initial(ch: AutomatonConfigWithWithEncParametricDescr) =
    new ParametricState(0, ch)

  protected[levenshteinautomaton]
  def apply(positions: Position*)(implicit c: DefaultAutomatonConfig): NonParametricState =
    State(ImageSet(positions: _*))

  protected[levenshteinautomaton]
  def apply(imageSet: ImageSet)(implicit c: DefaultAutomatonConfig) =
    if (imageSet.isEmpty) FailureState
    else new StandardState(imageSet, c)

  protected[levenshteinautomaton]
  def apply(encodedState: Int, statesCount: Int)
           (implicit c: AutomatonConfigWithWithEncParametricDescr): ParametricState =
    new ParametricState(encodedState, c)
}