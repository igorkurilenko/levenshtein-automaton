package io.itdraft.levenshteinautomaton.description.nonparametric

import io.itdraft.levenshteinautomaton.DefaultAutomatonConfig
import org.specs2.mutable.Specification

class EmptyImageSetSpec extends Specification {
  implicit val _ = DefaultAutomatonConfig("10101010", degree = 5)

  "has not minBoundary" >> {
    EmptyImageSet.minBoundary must throwA[NoSuchElementException]
  }

  "is empty" >> {
    EmptyImageSet.isEmpty must beTrue
  }

  "size is 0" >> {
    EmptyImageSet.size must be equalTo 0
  }

  "reducedAdd" should {
    "return NonEmptyImageSet" in {
      EmptyImageSet.reducedAdd(0 ^# 0).isEmpty must beFalse
    }
  }

  "equals to EmptyImageSet" >> {
    EmptyImageSet != EmptyImageSet.reducedAdd(0 ^# 0) must beTrue
    EmptyImageSet == EmptyImageSet must beTrue
  }
}
