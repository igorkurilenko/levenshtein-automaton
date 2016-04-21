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

import io.itdraft.levenshteinautomaton.description._

/**
  * A class to represent the failure state of the Levenshtein-automaton.
  */
protected[levenshteinautomaton] object FailureState extends NonparametricState {

  /**
    * Tests if it's a failure state or not. Returns `true`.
    */
  val isFailure = true

  /**
    * Tests if it's a final state or not. Returns `false`.
    */
  val isFinal = false

  val relevantSubwordMaxLength = 0

  val imageSet = EmptyImageSet

  /**
    * @inheritdoc
    *
    * @param other a `NonparametricState` to perform reduced union with.
    *
    * @return `other`.
    */
  def reducedUnion(other: NonparametricState): NonparametricState = other

  /**
    * The equality method for `FailureState`.
    *
    * @param that the other object to compare with.
    * @return `true` if other object is `FailureState` else `false`.
    */
  override def equals(that: scala.Any): Boolean = that match {
    case other: State => other.isFailure
    case _ => false
  }

  /**
    * The hash code for `FailureState` is `0`.
    */
  override val hashCode = 0

  /**
    * `String` representation of `FailureState` is `Ø`.
    */
  override val toString = "\u00D8"
}
