package io.itdraft.levenshteinautomaton.description.nonparametric

import io.itdraft.levenshteinautomaton.DefaultAutomatonConfig
import org.specs2.mutable.Specification

class EmptyImageSetSpec extends Specification {

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
      val c = DefaultAutomatonConfig("10101010", degree = 5)

      EmptyImageSet.reducedAdd(StandardPosition(0, 0, c)).isEmpty must beFalse
    }
  }

  "equals to EmptyImageSet" >> {
    val c = DefaultAutomatonConfig("10101010", degree = 5)

    EmptyImageSet != EmptyImageSet.reducedAdd(StandardPosition(0, 0, c)) must beTrue
    EmptyImageSet == EmptyImageSet must beTrue
  }

}
