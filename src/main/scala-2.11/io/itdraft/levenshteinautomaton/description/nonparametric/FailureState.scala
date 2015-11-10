package io.itdraft.levenshteinautomaton.description.nonparametric

import io.itdraft.levenshteinautomaton.description._

protected[levenshteinautomaton]
object FailureState extends NonParametricState {
  def imageSet: ImageSet = EmptyImageSet

  def automatonConfig = throw new NoSuchElementException

  val isFailure = true

  val isFinal = false

  def reducedUnion(other: NonParametricState): NonParametricState = other

  def transit(x: Int) = this

  override lazy val relevantSubwordMaxLength = 0

  override def equals(obj: scala.Any): Boolean =
    obj.isInstanceOf[State] && obj.asInstanceOf[State].isFailure

  override def toString = "\u00D8"
}
