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
import io.itdraft.levenshteinautomaton.description.State
import org.specs2.mutable.Specification

class FailureStateSpec extends Specification {
  implicit val _ = DefaultAutomatonConfig("10101010", degree = 5)

  "The failure state" should {
    "be failure" in {
      FailureState.isFailure must beTrue
    }

    "be not final" in {
      FailureState.isFinal must beFalse
    }

    "return other state on reduced union with other state" in {
      FailureState.reducedUnion(State(0 ^# 0, 1 ^# 0, 2 ^# 0)) must
        be equalTo State(0 ^# 0, 1 ^# 0, 2 ^# 0)
    }

    "transit to failure state only" in {
      FailureState.transit('x') must be equalTo FailureState
    }

    "be equal to failure state only" in {
      FailureState != State(0 ^# 0, 1 ^# 0, 2 ^# 0) must beTrue
      FailureState == FailureState must beTrue
    }
  }
}
