package io.itdraft.levenshteinautomaton.description.nonparametric

import io.itdraft.levenshteinautomaton.DefaultAutomatonConfig
import org.specs2.mutable.Specification

class StandardPositionSpec extends Specification {
  implicit val c = DefaultAutomatonConfig("10101010", degree = 5)

  "The 7^#4 position (w=8,n=5)" should {
    val position: Position = 7 ^# 4

    "be accepting" in {
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
      position.subsumes(TPosition(5, 5, c)) must beTrue
      position.subsumes(TPosition(6, 5, c)) must beTrue
    }

    "not subsume 4^#5 t-positions" in {
      position.subsumes(TPosition(4, 5, c)) must beFalse
    }

    "permit relevant subword max length equal to 1" in {
      position.relevantSubwordMaxLength must be equalTo 1
    }

    "have a hash code which is not equal to the 4^#7 position's hash code" in {
      position.hashCode must not equalTo (4 ^# 7).hashCode
    }

    "be less than 8^#4" in {
      position < (8 ^# 4) must beTrue
    }

    "be less than 6^#5" in {
      position < (6 ^# 5) must beTrue
    }

    "be greater than 8^#3" in {
      position > (8 ^# 3) must beTrue
    }
  }

  "The 6^#4 position (w=8,n=5)" should {
    "not be accepting" in {
      (6 ^# 4).isAccepting must beFalse
    }
  }
}