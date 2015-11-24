package io.itdraft.levenshteinautomaton.description.nonparametric

/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
