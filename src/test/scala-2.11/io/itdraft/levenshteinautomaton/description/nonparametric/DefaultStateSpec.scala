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

import io.itdraft.levenshteinautomaton.description.State
import org.specs2.mutable.Specification
import org.specs2.specification.Tables

class DefaultStateSpec extends Specification with Tables {

  import io.itdraft.levenshteinautomaton._
  import io.itdraft.levenshteinautomaton.description.nonparametric._

  "The {7^#5, 8^#5} state" should {
    implicit val _ = createLevenshteinAutomatonConfig("10101010", degree = 5)
    val state = State(7 ^# 5, 8 ^# 5)

    "have a min boundary equal to 7" in {
      state.minBoundary must be equalTo 7
    }

    "be not failure if w=8, n=5" in {
      state.isFailure must beFalse
    }

    "be final if w=8, n=5" in {
      state.isFinal must beTrue
    }

    "be equal to the {8^#5,7^#5} state" in {
      state must be equalTo State(8 ^# 5, 7 ^# 5)
    }

    "be not equal to the {8^#5} state" in {
      state must not equalTo State(8 ^# 5)
    }

    "have a hash code equal to the {8^#5,7^#5} state's hash code" in {
      state.hashCode must be equalTo State(8 ^# 5, 7 ^# 5).hashCode
    }

    "have a hash code which is not equal to the {8^#5} state's hash code" in {
      state.hashCode must not equalTo State(8 ^# 5).hashCode
    }

    "have a hash code which is not equal to the failure state's hash code" in {
      state.hashCode must not equalTo FailureState.hashCode
    }
  }

  "The {7^#5} state" should {
    implicit val _ = createLevenshteinAutomatonConfig("10101010", degree = 5)

    "be not final if w=8, n=5" in {
      State(7 ^# 5).isFinal must beFalse
    }
  }

  "Standard state reduced union operation" should {
    implicit val _ = createLevenshteinAutomatonConfig("10101010", degree = 5)

    "generate new state with eliminated subsumed positions" in {
      val m = State(0 ^# 0, 1 ^# 0, 2 ^# 0)
      val n = State(0 ^# 1, 1 ^# 1, 2 ^# 1)
      val reducedUnion = m |~ n

      reducedUnion must be equalTo State(0 ^# 0, 1 ^# 0, 2 ^# 0)
    }

    "be associative" in {
      val m = State(0 ^# 0, 1 ^# 0, 2 ^# 0)
      val n = State(0 ^# 1, 1 ^# 1, 2 ^# 1)
      val s = State(3 ^# 0)

      ((m |~ n) |~ s) == (m |~ (n |~ s)) must beTrue
    }

    "be commutative" in {
      val m = State(0 ^# 0, 1 ^# 0, 2 ^# 0)
      val n = State(0 ^# 1, 1 ^# 1, 2 ^# 1)

      (m |~ n) == (n |~ m) must beTrue
    }
  }

  "Transition of various states and characteristic vectors" should {
    "generate new states according to definitions in the algorithm's publication" in {
      // todo: tests for states including t-positions
      "from" | "to" | "word" | "alpha" | "degree" | "inclTranspositions" |>
        (0, 0) :: Nil ! (0, 1) ::(1, 1) ::(3, 2) :: Nil ! "0010000000" ! '1' ! 5 ! false |
        (1, 2) ::(2, 2) ::(3, 2) ::(4, 2) :: Nil ! (4, 2) ::(2, 3) ::(1, 3) :: Nil ! "1001000010" ! '1' ! 5 ! false |
        (4, 2) ::(2, 3) ::(1, 3) :: Nil ! (2, 4) ::(1, 4) ::(5, 2) :: Nil ! "0000100000" ! '1' ! 5 ! false |
        (7, 5) ::(9, 4) :: Nil ! (9, 5) ::(10, 5) :: Nil ! "0000000000" ! '1' ! 5 ! false |
        (9, 5) ::(10, 5) :: Nil ! Nil ! "0000000000" ! '1' ! 5 ! false | {
        (from, to, word, alpha, degree, inclTranspositions) =>
          implicit val config = createLevenshteinAutomatonConfig(word, degree, inclTranspositions)

          LazyLevenshteinAutomaton(config).getNextState(from, alpha) must be equalTo to
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