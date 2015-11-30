package io.itdraft.levenshteinautomaton.description.parametric

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
import io.itdraft.levenshteinautomaton.description.parametric.coding.ParametricStateCodec

/**
  * Represents the parametric state of the Levenshtein-automaton.
  *
  * @note Encapsulates an encoded parametric state.
  */
protected[levenshteinautomaton]
class ParametricState(val asEncodedInteger: Int,
                      automatonConfig: LevenshteinAutomatonConfig) extends State {

  import io.itdraft.levenshteinautomaton._

  private lazy val w = automatonConfig.w
  private lazy val n = automatonConfig.n

  private lazy val parametricDescription = automatonConfig.getParametricDescriptionFactory
    .getEncodedParametricDescription(n, automatonConfig.inclTransposition)
  private lazy val statesCount = parametricDescription.getParametricStatesCount

  /**
    * Returns minimal boundary of this `State`.
    */
  lazy val minBoundary = ParametricStateCodec.getMinBoundary(asEncodedInteger, statesCount)

  /**
    * Tests if it's a final state or not.
    */
  lazy val isFinal = ParametricStateCodec.isFinal(asEncodedInteger, automatonConfig)

  /**
    * Tests if it's a failure state or not.
    */
  lazy val isFailure = ParametricStateCodec.isFailure(asEncodedInteger, statesCount)
}

protected[levenshteinautomaton] object ParametricState {
  /**
    * Returns the initial state for the parametrically described Levenshtein-automaton.
    */
  def initial(ch: LevenshteinAutomatonConfig) = new ParametricState(0, ch)

  def apply(encodedState: Int)
           (implicit c: LevenshteinAutomatonConfig): ParametricState =
    new ParametricState(encodedState, c)
}
