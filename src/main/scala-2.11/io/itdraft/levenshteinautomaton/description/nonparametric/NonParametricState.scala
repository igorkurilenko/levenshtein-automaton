package io.itdraft.levenshteinautomaton.description.nonparametric

import io.itdraft.levenshteinautomaton.DefaultAutomatonConfig
import io.itdraft.levenshteinautomaton.description._

protected[levenshteinautomaton]
abstract class NonParametricState extends State {

  lazy val minBoundary = imageSet.minBoundary

  def automatonConfig: DefaultAutomatonConfig

  /**
   * Returns new `NonParametricState` produced by elementary transition
   * for this `NonParametricState`.
   * @param x Symbol from the alphabet.
   */
  def transit(x: Int): NonParametricState

  /**
   * A set of positions of this `NonParametricState`.
   */
  def imageSet: ImageSet

  /**
   * Returns a new `State` which is obtained by union of this `NonParametricState`
   * with `other` and omission of positions that are subsumed by other positions.
   */
  def reducedUnion(other: NonParametricState): NonParametricState

  /**
   * Returns a new `NonParametricState` which is obtained by union
   * of this `NonParametricState` with `other` and omission of positions
   * that are subsumed by other positions.
   */
  def |~(other: NonParametricState) = reducedUnion(other)

  def contains(position: Position) = imageSet.contains(position)

  def size = imageSet.size

  override def toString = imageSet.toString
}
