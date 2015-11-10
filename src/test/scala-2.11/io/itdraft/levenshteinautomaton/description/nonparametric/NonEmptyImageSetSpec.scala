package io.itdraft.levenshteinautomaton.description.nonparametric

import io.itdraft.levenshteinautomaton.DefaultAutomatonConfig
import org.specs2.mutable.Specification

class NonEmptyImageSetSpec extends Specification {
  implicit val _ = DefaultAutomatonConfig("10101010", degree = 5)

  "reducedAdd" should {
    "return image set which does not contain any position that is subsumed" +
      "by any element of the image set" in {
      (2 ^# 2) +~: (0 ^# 2) +~: ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2) must
        be equalTo ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2)

      (1 ^# 0) +~: ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2) must
        be equalTo ImageSet(1 ^# 0)
    }
  }

  "subsumes" >> {
    ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2).subsumes(3 ^# 3) must beTrue
    ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2).subsumes(1 ^# 0) must beFalse
  }

  "minBoundary" >> {
    ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2).minBoundary must be equalTo 0
    ImageSet(7 ^# 5, 9 ^# 4).minBoundary must be equalTo 7
  }

  "contains" >> {
    ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2).contains(3 ^# 2) must beTrue
    ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2).contains(2 ^# 3) must beFalse
  }

  "is not empty" >> {
    ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2).isEmpty must beFalse
  }

  "size" >> {
    ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2).size must be equalTo 3
    ImageSet(0 ^# 1, 1 ^# 1).size must be equalTo 2
    ImageSet(0 ^# 1).size must be equalTo 1
  }

  "equals" >> {
    ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2) must be equalTo ImageSet(3 ^# 2, 0 ^# 1, 1 ^# 1)
    ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2) must not equalTo ImageSet(3 ^# 2, 1 ^# 1)
    ImageSet(0 ^# 1, 1 ^# 1) must not equalTo ImageSet(3 ^# 2, 0 ^# 1, 1 ^# 1)
    ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2) must not equalTo ImageSet(2 ^# 3, 0 ^# 1, 1 ^# 1)
    ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2) != EmptyImageSet must beTrue
  }

  "hashCode" >> {
    ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2).hashCode must
      be equalTo ImageSet(3 ^# 2, 0 ^# 1, 1 ^# 1).hashCode
    ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2).hashCode must
      not equalTo ImageSet(3 ^# 2, 1 ^# 1).hashCode
    ImageSet(0 ^# 1, 1 ^# 1).hashCode must
      not equalTo ImageSet(3 ^# 2, 0 ^# 1, 1 ^# 1).hashCode
    ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2).hashCode must
      not equalTo ImageSet(2 ^# 3, 0 ^# 1, 1 ^# 1).hashCode
    ImageSet(0 ^# 1, 1 ^# 1, 3 ^# 2).hashCode must
      not equalTo EmptyImageSet.hashCode
  }
}
