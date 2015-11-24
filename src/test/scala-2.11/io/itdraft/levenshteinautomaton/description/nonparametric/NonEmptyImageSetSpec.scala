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
import org.specs2.mutable.Specification

class NonEmptyImageSetSpec extends Specification {
  implicit val _ = DefaultAutomatonConfig("10101010", degree = 5)

  "The {0^#1, 1^#1, 3^#2} image set" should {
    val imageSet = ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2)

    "be the same after reduced addition of the 2^#2, 0^#2" in {
      (2 ^# 2) +~: (0 ^# 2) +~: imageSet must
        be equalTo imageSet
    }

    "become the {1^#0} image set after reduced addition of the 1^#0" in {
      (1 ^# 0) +~: imageSet must
        be equalTo ImageSet(1 ^# 0)
    }

    "subsume 3^#3" in {
      imageSet.subsumes(3 ^# 3) must beTrue
    }

    "not subsume 1^#0" in {
      imageSet.subsumes(1 ^# 0) must beFalse
    }

    "have a min boundary equal to 0" in {
      imageSet.minBoundary must be equalTo 0
    }

    "contain 3^#2" in {
      imageSet.contains(3 ^# 2) must beTrue
    }

    "not contain 2^#3" in {
      imageSet.contains(2 ^# 3) must beFalse
    }

    "be not empty" in {
      imageSet.isEmpty must beFalse
    }

    "have a size equal to 3" in {
      imageSet.size must be equalTo 3
    }

    "be equal to the {3^#2, 0^#1, 1^#1} (another order) image set" in {
      imageSet must be equalTo ImageSet(3 ^# 2, 0 ^# 1, 1 ^# 1)
    }

    "not be equal to the {3^#2, 1^#1} image set" in {
      imageSet must not equalTo ImageSet(3 ^# 2, 1 ^# 1)
      ImageSet(0 ^# 1, 1 ^# 1) must not equalTo imageSet
    }

    "not be equal to the {2 ^# 3, 0 ^# 1, 1 ^# 1} image set" in {
      imageSet must not equalTo ImageSet(2 ^# 3, 0 ^# 1, 1 ^# 1)
    }

    "be not equal to the empty image set" in {
      imageSet != EmptyImageSet must beTrue
    }

    "have a hash code which is equal to a hash code of the {3^#2, 0^#1, 1^#1}" in {
      imageSet.hashCode must
        be equalTo ImageSet(3 ^# 2, 0 ^# 1, 1 ^# 1).hashCode
    }

    "have a hash code which is not equal to a hash code of the {2^#3, 0^#1, 1^#1}" in {
      imageSet.hashCode must
        not equalTo ImageSet(2 ^# 3, 0 ^# 1, 1 ^# 1).hashCode
    }

    "have a hash code which is not equal to a hash code of the {3^#2, 1^#1}" in {
      imageSet.hashCode must
        not equalTo ImageSet(3 ^# 2, 1 ^# 1).hashCode
    }

    "have a hash code which is not equal to a hash code of the empty image set" in {
      imageSet.hashCode must
        not equalTo EmptyImageSet.hashCode
    }
  }

  "The {7^#5, 8^#5} image set" should {
    val imageSet = ImageSet(7 ^# 5, 8 ^# 5)

    "have a min boundary equal to 7" in {
      imageSet.minBoundary must be equalTo 7
    }

    "have a size equal to 2" in {
      imageSet.size must be equalTo 2
    }
  }

  "The {7^#5} image set" should {
    "have a size equal to 1" in {
      ImageSet(7 ^# 5).size must be equalTo 1
    }
  }
}
