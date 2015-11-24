package io.itdraft.levenshteinautomaton.description.nonparametric

/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import io.itdraft.levenshteinautomaton.description._

protected[levenshteinautomaton]
abstract class NonparametricState extends State {

  /**
    * A set of positions of this `NonParametricState`.
    */
  def imageSet: ImageSet

  /**
   * Returns new `NonParametricState` produced by elementary transition
   * for this `NonParametricState`.
   * @param x Symbol from the alphabet.
   */
  def transit(x: Int): NonparametricState

  /**
   * Returns a new `State` which is obtained by union of this `NonParametricState`
   * with `other` and omission of positions that are subsumed by other positions.
   */
  def reducedUnion(other: NonparametricState): NonparametricState

  /**
   * Returns a new `NonParametricState` which is obtained by union
   * of this `NonParametricState` with `other` and omission of positions
   * that are subsumed by other positions.
   */
  def |~(other: NonparametricState) = reducedUnion(other)
}
