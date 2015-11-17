package io.itdraft.levenshteinautomaton.description.nonparametric

import io.itdraft.levenshteinautomaton.description._

protected[levenshteinautomaton]
abstract class NonparametricState extends State {

  /**
    * A set of positions of this `NonParametricState`.
    */
  def imageSet: ImageSet

  /**
   * Returns new `NonParametricState` produced by elementary transition
   * for this `NonParametricState`.
   * @param x Symbol from the alphabet.
   */
  def transit(x: Int): NonparametricState

  /**
   * Returns a new `State` which is obtained by union of this `NonParametricState`
   * with `other` and omission of positions that are subsumed by other positions.
   */
  def reducedUnion(other: NonparametricState): NonparametricState

  /**
   * Returns a new `NonParametricState` which is obtained by union
   * of this `NonParametricState` with `other` and omission of positions
   * that are subsumed by other positions.
   */
  def |~(other: NonparametricState) = reducedUnion(other)
}
