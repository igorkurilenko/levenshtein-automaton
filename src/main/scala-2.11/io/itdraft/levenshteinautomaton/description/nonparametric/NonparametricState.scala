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

import io.itdraft.levenshteinautomaton.LevenshteinAutomatonConfig
import io.itdraft.levenshteinautomaton.description._

/**
  * Represents the state of the Levenshtein-automaton,
  * which doesn't base on parametric description.
  */
protected[levenshteinautomaton] abstract class NonparametricState extends State {

  /**
    * Returns minimal boundary of this `State`.
    */
  lazy val minBoundary = {
    var min = Integer.MAX_VALUE
    imageSet.foreach { p =>
      if (p.i < min) min = p.i
    }
    min
  }

  /**
    * An image set of positions of this `NonParametricState`.
    */
  def imageSet: ImageSet

  /**
    * Returns a new `NonparametricState` which is obtained by union
    * of this `NonParametricState` with `other` and by omission of positions
    * that are subsumed by any other positions.
    *
    * @param other a `NonparametricState` to perform reduced union with.
    *
    * @return a new instance of `NonparametricState` produced by reduced union
    *         of this `NonparametricState` and `other`.
    */
  def reducedUnion(other: NonparametricState): NonparametricState

  /**
    * Returns a new `NonparametricState` which is obtained by union
    * of this `NonParametricState` with `other` and by omission of positions
    * that are subsumed by any other positions.
    *
    * @param other a `NonparametricState` to perform reduced union with.
    *
    * @return a new instance of `NonparametricState` produced by reduced union
    *         of this `NonparametricState` and `other`.
    */
  def |~(other: NonparametricState) = reducedUnion(other)
}

protected[levenshteinautomaton] object NonparametricState {
  /**
    * Returns the initial state for the nonparametrically
    * described Levenshtein-automaton.
    */
  def initial(ch: LevenshteinAutomatonConfig): NonparametricState = {
    implicit val _ = ch
    State(0 ^# 0)
  }

  def apply(positions: Position*)(implicit c: LevenshteinAutomatonConfig): NonparametricState =
    NonparametricState(ImageSet(positions: _*))

  def apply(imageSet: ImageSet)(implicit c: LevenshteinAutomatonConfig) =
    if (imageSet.isEmpty) FailureState
    else new DefaultState(imageSet, c)
}
