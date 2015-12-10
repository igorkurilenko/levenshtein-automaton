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

import io.itdraft.levenshteinautomaton.LevenshteinAutomatonConfig
import io.itdraft.levenshteinautomaton.description._

/**
  * Represents the state of the nonparametrically described Levenshtein-automaton.
  */
protected[levenshteinautomaton]
class DefaultState(val imageSet: ImageSet,
                   val automatonConfig: LevenshteinAutomatonConfig) extends NonparametricState {
  assert(!imageSet.isEmpty)

  import io.itdraft.levenshteinautomaton._

  private lazy val w = automatonConfig.w
  private lazy val n = automatonConfig.n

  private implicit val _ = automatonConfig

  val isFailure = false

  lazy val isFinal = imageSet.exists(_.isAccepting)

  def reducedUnion(other: NonparametricState): NonparametricState =
    if (other.isFailure) this
    else NonparametricState(other.imageSet.fold(imageSet)(_.reducedAdd(_)))

  override def equals(obj: scala.Any): Boolean = obj match {
    case other: NonparametricState if !other.isFailure =>
      imageSet == other.imageSet

    case _ => false
  }

  override lazy val hashCode = imageSet.hashCode

  override def toString = imageSet.toString
}
