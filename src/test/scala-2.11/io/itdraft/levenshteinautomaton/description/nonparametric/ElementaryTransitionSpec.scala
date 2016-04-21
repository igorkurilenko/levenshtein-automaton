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

import io.itdraft.levenshteinautomaton.description.{DefaultCharacteristicVector, State}
import org.specs2.mutable.Specification
import org.specs2.specification.Tables

class ElementaryTransitionSpec extends Specification with Tables {

  import io.itdraft.levenshteinautomaton._
  import io.itdraft.levenshteinautomaton.description.nonparametric._
  import io.itdraft.levenshteinautomaton.util.StringUtil._

  "Elementary transition for various positions" should {
    "perform transition according to the algorithm's publication" in {
      // todo: tests for table including transpositions
      "position" | "word" | "symbol" | "degree" | "inclTranspositions" | "state" |>
        (0, 0) ! "" ! 'x' ! 0 ! false ! Nil |
        (0, 0) ! "" ! 'x' ! 0 ! true ! Nil |
        (0, 0) ! "x" ! 'x' ! 0 ! false ! (1, 0) :: Nil |
        (0, 0) ! "x" ! 'x' ! 0 ! true ! (1, 0) :: Nil |
        (0, 0) ! "" ! 'x' ! 1 ! false ! (0, 1) :: Nil |
        (0, 0) ! "" ! 'x' ! 1 ! true ! (0, 1) :: Nil |
        (2, 0) ! "00001011" ! '1' ! 5 ! false ! (2, 1) ::(3, 1) ::(5, 2) :: Nil |
        (2, 0) ! "00101011" ! '1' ! 5 ! false ! (3, 0) :: Nil |
        (2, 0) ! "00000000" ! '1' ! 5 ! false ! (2, 1) ::(3, 1) :: Nil |
        (2, 0) ! "00000001" ! '1' ! 5 ! false ! (2, 1) ::(3, 1) ::(8, 5) :: Nil |
        (7, 0) ! "00000001" ! '1' ! 5 ! false ! (8, 0) :: Nil |
        (7, 0) ! "00000000" ! '1' ! 5 ! false ! (7, 1) ::(8, 1) :: Nil |
        (8, 0) ! "00000000" ! '1' ! 5 ! false ! (8, 1) :: Nil |
        (8, 5) ! "00000000" ! '1' ! 5 ! false ! Nil | {
        (position, word, symbol, degree, inclTranspositions, state: List[(Int, Int)]) =>
          implicit val automatonConfig = createLevenshteinAutomatonConfig(word, degree, inclTranspositions)
          val transition = ElementaryTransition()
          val (i, e) = position
          val v = DefaultCharacteristicVector(symbol, toCodePoints(word), i)

          transition(i ^# e, v) must be equalTo state
      }
    }
  }

  implicit def conversionToState(stateFormat: List[(Int, Int)])
                                (implicit c: LevenshteinAutomatonConfig): NonparametricState =
    stateFormat match {
      case Nil => FailureState
      case ps => State(ps.map(t => t._1 ^# t._2): _*)
    }
}
