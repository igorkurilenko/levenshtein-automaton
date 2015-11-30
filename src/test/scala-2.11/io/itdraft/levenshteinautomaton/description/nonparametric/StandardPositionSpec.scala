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

class StandardPositionSpec extends Specification {
  import io.itdraft.levenshteinautomaton._

  implicit val _ = createLevenshteinAutomatonConfig("10101010", degree = 5)

  "The 7^#4 position (w=8,n=5)" should {
    val position: Position = 7 ^# 4

    "be an accepting" in {
      position.isAccepting must beTrue
    }

    "subsume 7^#5, 8^#5, 6^#5" in {
      position.subsumes(6 ^# 5) must beTrue
      position.subsumes(7 ^# 5) must beTrue
      position.subsumes(8 ^# 5) must beTrue
    }

    "not subsume 0^#0, 7^#4, 8^#4" in {
      position.subsumes(0 ^# 0) must beFalse
      position.subsumes(7 ^# 4) must beFalse
      position.subsumes(8 ^# 4) must beFalse
    }

    "subsume 5^#5, 6^#5 t-positions" in {
      position.subsumes(5.t ^# 5) must beTrue
      position.subsumes(6.t ^# 5) must beTrue
    }

    "not subsume 4^#5 t-positions" in {
      position.subsumes(4.t ^# 5) must beFalse
    }

    "permit relevant subword max length equal to 1" in {
      position.relevantSubwordMaxLength must be equalTo 1
    }

    "have a hash code which is equal to the 7^#4 position's hash code" in {
      position.hashCode must be equalTo (7 ^# 4).hashCode
    }

    "have a hash code which is not equal to the 7^#4 t-position's hash code" in {
      position.hashCode must not equalTo (7.t ^# 4).hashCode
    }

    "be less than 8^#4 (both position and t-position)" in {
      position < (8 ^# 4) must beTrue
      position < (8.t ^# 4) must beTrue
    }

    "be less than 6^#5 (both position and t-position)" in {
      position < (6 ^# 5) must beTrue
      position < (6.t ^# 5) must beTrue
    }

    "be greater than 8^#3 (both position and t-position)" in {
      position > (8 ^# 3) must beTrue
      position > (8.t ^# 3) must beTrue
    }

    "be greater than the 7^#4 t-position" in {
      position > (7.t ^# 4) must beTrue
    }
  }

  "The 5^#3 position (w=8,n=5)" should {
    val position: Position = 5 ^# 3

    "not be an accepting" in {
      position.isAccepting must beFalse
    }

    "have a hash code which is not equal to the 3^#5 position's hash code" in {
      position.hashCode must not equalTo (3 ^# 5).hashCode
    }
  }
}