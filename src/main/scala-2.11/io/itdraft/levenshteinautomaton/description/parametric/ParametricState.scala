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

import io.itdraft.levenshteinautomaton.AutomatonConfigWithEncodedParametricDescription
import io.itdraft.levenshteinautomaton.description._
import io.itdraft.levenshteinautomaton.description.parametric.coding.ParametricStateCodec

protected[levenshteinautomaton]
class ParametricState(state: Int,
                      val automatonConfig: AutomatonConfigWithEncodedParametricDescription)
  extends State {

  private lazy val w = automatonConfig.w
  private lazy val n = automatonConfig.n

  private lazy val parametricDescription = automatonConfig.encodedParametricDescription
  private lazy val statesCount = parametricDescription.getStatesCount

  lazy val relevantSubwordMaxLength = Math.min(2 * n + 1, w - minBoundary)

  def minBoundary = ParametricStateCodec.getMinBoundary(state, statesCount)

  lazy val isFinal = ParametricStateCodec.isFinal(state,
    w, n, automatonConfig.encodedParametricDescription)


  lazy val isFailure = ParametricStateCodec.isFailure(state, statesCount)

  def transit(xCodePoint: Int) = {
    val nextState = ParametricStateCodec.transit(xCodePoint, state,
      w, n, automatonConfig.word, parametricDescription)

    new ParametricState(nextState, automatonConfig)
  }
}
