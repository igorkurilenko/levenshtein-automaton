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

import org.specs2.mutable.Specification

class EmptyImageSetSpec extends Specification {
  import io.itdraft.levenshteinautomaton._

  implicit val _ = createLevenshteinAutomatonConfig("10101010", degree = 5)

  "The empty image set" should {
    "be empty" in {
      EmptyImageSet.isEmpty must beTrue
    }

    "have a size equal to 0" in {
      EmptyImageSet.size must be equalTo 0
    }

    "return non empty image set on reduced addition" in {
      EmptyImageSet.reducedAdd(0 ^# 0).isEmpty must beFalse
    }

    "be equal to empty image set only" in {
      EmptyImageSet != EmptyImageSet.reducedAdd(0 ^# 0) must beTrue
      EmptyImageSet == EmptyImageSet must beTrue
    }
  }
}
