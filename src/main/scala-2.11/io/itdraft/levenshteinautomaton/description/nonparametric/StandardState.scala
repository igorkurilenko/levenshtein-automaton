package io.itdraft.levenshteinautomaton.description.nonparametric

import io.itdraft.levenshteinautomaton.DefaultAutomatonConfig
import io.itdraft.levenshteinautomaton.description._

protected[levenshteinautomaton]
class StandardState(val imageSet: ImageSet,
                    val automatonConfig: DefaultAutomatonConfig)
  extends NonparametricState {

  assert(!imageSet.isEmpty)

  private lazy val w = automatonConfig.w
  private lazy val n = automatonConfig.n

  private implicit val _ = automatonConfig

  private val elementaryTransition = ElementaryTransition()

  lazy val relevantSubwordMaxLength = Math.min(2 * n + 1, w - minBoundary)

  lazy val minBoundary = imageSet.minBoundary

  val isFailure = false

  lazy val isFinal = imageSet.exists(_.isAccepting)

  def reducedUnion(other: NonparametricState): NonparametricState =
    if (other.isFailure) this
    else State(other.imageSet.fold(imageSet)(_.reducedAdd(_)))

  def transit(x: Int) =
    imageSet.fold(FailureState: NonparametricState) { (state, position) =>
      val v = relevantSubwordCharacteristicVector(position, x)

      state |~ elementaryTransition(position, v)
    }

  private def relevantSubwordCharacteristicVector(position: Position, x: Int) =
    DefaultCharacteristicVector(x, automatonConfig.word,
      position.i, position.i + position.relevantSubwordMaxLength)

  override def equals(obj: scala.Any): Boolean = obj match {
    case other: NonparametricState if !other.isFailure =>
      imageSet == other.imageSet

    case _ => false
  }

  override lazy val hashCode = imageSet.hashCode

  override def toString = imageSet.toString
}
