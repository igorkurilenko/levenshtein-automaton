package io.itdraft.levenshteinautomaton.description.nonparametric

import io.itdraft.levenshteinautomaton.DefaultAutomatonConfig
import org.specs2.mutable.Specification

class StandardPositionSpec extends Specification {
  implicit val c = DefaultAutomatonConfig("10101010", degree = 5)

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
      position.subsumes(TPosition(5, 5, c)) must beTrue
      position.subsumes(TPosition(6, 5, c)) must beTrue
    }

    "not subsume 4^#5 t-positions" in {
      position.subsumes(TPosition(4, 5, c)) must beFalse
    }

    "permit relevant subword max length equal to 1" in {
      position.relevantSubwordMaxLength must be equalTo 1
    }

    "have a hash code which is equal to the 7^#4 position's hash code" in {
      position.hashCode must be equalTo (7 ^# 4).hashCode
    }

    "have a hash code which is not equal to the 7^#4 t-position's hash code" in {
      position.hashCode must not equalTo TPosition(7, 4, c).hashCode
    }

    "be less than 8^#4 (both position and t-position)" in {
      position < (8 ^# 4) must beTrue
      position < TPosition(8, 4, c) must beTrue
    }

    "be less than 6^#5 (both position and t-position)" in {
      position < (6 ^# 5) must beTrue
      position < TPosition(6, 5, c) must beTrue
    }

    "be greater than 8^#3 (both position and t-position)" in {
      position > (8 ^# 3) must beTrue
      position > TPosition(8, 3, c) must beTrue
    }

    "be greater than the 7^#4 t-position" in {
      position > TPosition(7, 4, c) must beTrue
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