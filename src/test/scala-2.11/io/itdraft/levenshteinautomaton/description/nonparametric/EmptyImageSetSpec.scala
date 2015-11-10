package io.itdraft.levenshteinautomaton.description.nonparametric

import io.itdraft.levenshteinautomaton.DefaultAutomatonConfig
import org.specs2.mutable.Specification

class EmptyImageSetSpec extends Specification {
  implicit val _ = DefaultAutomatonConfig("10101010", degree = 5)

  "The empty image set" should {
    "have not a min boundary" in {
      EmptyImageSet.minBoundary must throwA[NoSuchElementException]
    }

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
