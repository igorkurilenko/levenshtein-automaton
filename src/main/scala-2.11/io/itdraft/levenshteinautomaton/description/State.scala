package io.itdraft.levenshteinautomaton.description

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

import io.itdraft.levenshteinautomaton.description.nonparametric._
import io.itdraft.levenshteinautomaton.description.parametric.ParametricState

/**
  * A class to represent a state of the Levenshtein-automaton.
  */
trait State {

  /**
    * Returns minimal boundary of this `State`.
    */
  def minBoundary: Int

  /**
    * Tests if it's a failure state or not.
    */
  def isFailure: Boolean

  /**
    * Tests if it's a final state or not.
    */
  def isFinal: Boolean
}

protected[levenshteinautomaton] object State {
  import io.itdraft.levenshteinautomaton._

  /**
    * Factory method to create an initial state.
    *
    * @return the initial state as instance of `NonparametricState` or `ParametricState`.
    *         It depends on whether parametric description exists.
    */
  def initial()(implicit ch: LevenshteinAutomatonConfig): State =
    Option(ch.getParametricDescriptionFactory.getEncodedParametricDescription(ch.n, ch.inclTransposition)) match {
      case Some(_) => ParametricState.initial(ch)
      case None => NonparametricState.initial(ch)
    }

  def apply(positions: Position*)(implicit c: LevenshteinAutomatonConfig): NonparametricState =
    NonparametricState(positions: _*)

  def apply(imageSet: ImageSet)(implicit c: LevenshteinAutomatonConfig) =
    NonparametricState(imageSet)
}