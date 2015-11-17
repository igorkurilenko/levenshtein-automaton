package io.itdraft.levenshteinautomaton.description.nonparametric

import io.itdraft.levenshteinautomaton.description._

/**
  * A class to represent the nonparametric failure state.
  */
protected[levenshteinautomaton] object FailureState extends NonparametricState {

  /**
    * Tests if it's a failure state or not. Always `true` for `FailureState`.
    */
  val isFailure = true

  /**
    *Tests if it's a final state or not. Always `false` for `FailureState`.
    */
  val isFinal = false

  /**
    * The max relevant subword length of `FailureState` is 0.
    */
  override lazy val relevantSubwordMaxLength = 0

  /**
    * The `FailureState` image set is `EmptyImageSet`.
    */
  override val imageSet = EmptyImageSet

  /**
    * The reduced union of two states is a union of this states with omission
    * of states that are subsumed by other states.
    *
    * @param other `NonparametricState` to execute reduced union with.
    * @return The reduced union of `FailureState` always returns `other`.
    */
  def reducedUnion(other: NonparametricState): NonparametricState = other

  /**
    * Returns a new `State` produced by elementary transitions of this `State`'s positions.
    * `FailureState` can transit to `FailureState` only.
    *
    * @param symbolCodePoint Symbol's code point.
    */
  def transit(symbolCodePoint: Int) = this

  /**
    * The equality method for `FailureState`.
    */
  override def equals(obj: scala.Any): Boolean = obj match {
    case other: State => other.isFailure
    case _ => false
  }

  /**
    * The hash code for `FailureState`.
    */
  override val hashCode = 0

  /**
    * Creates a `String` representation of this object.
    */
  override val toString = "\u00D8"
}
