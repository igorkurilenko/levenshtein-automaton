package io.itdraft.levenshteinautomaton.description.nonparametric

import io.itdraft.levenshteinautomaton.DefaultAutomatonConfig
import org.specs2.mutable.Specification

class NonEmptyImageSetSpec extends Specification {
  implicit val _ = DefaultAutomatonConfig("10101010", degree = 5)

  "The {0^#1, 1^#1, 3^#2} image set" should {
    "be the same after reduced addition of the 2^#2, 0^#2" in {
      (2 ^# 2) +~: (0 ^# 2) +~: ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2) must
        be equalTo ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2)
    }

    "become the {1^#0} image set after reduced addition of the 1^#0" in {
      (1 ^# 0) +~: ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2) must
        be equalTo ImageSet(1 ^# 0)
    }

    "subsume 3^#3" in {
      ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2).subsumes(3 ^# 3) must beTrue
    }

    "not subsume 1^#0" in {
      ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2).subsumes(1 ^# 0) must beFalse
    }

    "have a min boundary equal to 0" in {
      ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2).minBoundary must be equalTo 0
    }

    "contain 3^#2" in {
      ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2).contains(3 ^# 2) must beTrue
    }

    "not contain 2^#3" in {
      ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2).contains(2 ^# 3) must beFalse
    }

    "be not empty" in {
      ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2).isEmpty must beFalse
    }

    "have a size equal to 3" in {
      ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2).size must be equalTo 3
    }

    "be equal to the {3^#2, 0^#1, 1^#1} (another order) image set" in {
      ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2) must be equalTo ImageSet(3 ^# 2, 0 ^# 1, 1 ^# 1)
    }

    "not be equal to the {3^#2, 1^#1} image set" in {
      ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2) must not equalTo ImageSet(3 ^# 2, 1 ^# 1)
      ImageSet(0 ^# 1, 1 ^# 1) must not equalTo ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2)
    }

    "not be equal to the {2 ^# 3, 0 ^# 1, 1 ^# 1} image set" in {
      ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2) must not equalTo ImageSet(2 ^# 3, 0 ^# 1, 1 ^# 1)
    }

    "be not equal to the empty image set" in {
      ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2) != EmptyImageSet must beTrue
    }

    "have hash code which is equal to a hash code of the {3^#2, 0^#1, 1^#1}" in {
      ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2).hashCode must
        be equalTo ImageSet(3 ^# 2, 0 ^# 1, 1 ^# 1).hashCode
    }

    "have hash code which is not equal to a hash code of the {2^#3, 0^#1, 1^#1}" in {
      ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2).hashCode must
        not equalTo ImageSet(2 ^# 3, 0 ^# 1, 1 ^# 1).hashCode
    }

    "have hash code which is not equal to a hash code of the {3^#2, 1^#1}" in {
      ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2).hashCode must
        not equalTo ImageSet(3 ^# 2, 1 ^# 1).hashCode
    }

    "have hash code which is not equal to a hash code of the empty image set" in {
      ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2).hashCode must
        not equalTo EmptyImageSet.hashCode
    }
  }

  "The {7^#5, 9^#4} image set" should {
    "have a min boundary equal to 7" in {
      ImageSet(7 ^# 5, 9 ^# 4).minBoundary must be equalTo 7
    }

    "have a size equal to 2" in {
      ImageSet(7 ^# 5, 9 ^# 4).size must be equalTo 2
    }
  }

  "The {7^#5} image set" should {
    "have a size equal to 1" in {
      ImageSet(7 ^# 5).size must be equalTo 1
    }
  }
}
