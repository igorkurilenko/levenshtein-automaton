package io.itdraft.levenshteinautomaton.description.nonparametric

import io.itdraft.levenshteinautomaton.DefaultAutomatonConfig
import org.specs2.execute.Result
import org.specs2.mutable.Specification

class TPositionSpec extends Specification {

  implicit val c = DefaultAutomatonConfig("10101010", degree = 5)

  "The 6^#2 t-position (w=8,n=5)" should {
    val position: Position = TPosition(6, 2, c)

    "subsume the 6^#5 position" in {
      position.subsumes(6 ^# 5) must beTrue
    }

    "not subsume 5^#3, 6^#3, 6^#2, 7^#3" in {
      position.subsumes(5 ^# 3) must beFalse
      position.subsumes(6 ^# 2) must beFalse
      position.subsumes(6 ^# 3) must beFalse
      position.subsumes(7 ^# 3) must beFalse
    }

    "subsume 6^#3, 6^#4, 6^#5 t-positions" in {
      position.subsumes(TPosition(6, 3, c)) must beTrue
      position.subsumes(TPosition(6, 4, c)) must beTrue
      position.subsumes(TPosition(6, 5, c)) must beTrue
    }

    "not subsume 6^#2, 5^#4, 7^#5 t-positions" in {
      position.subsumes(TPosition(6, 2, c)) must beFalse
      position.subsumes(TPosition(5, 4, c)) must beFalse
      position.subsumes(TPosition(7, 5, c)) must beFalse
    }

    "permit relevant subword max length equal to 2" in {
      position.relevantSubwordMaxLength must be equalTo 2
    }

    "have a hash code which is equal to the 6^#2 t-position's hash code" in {
      position.hashCode must be equalTo TPosition(6, 2, c).hashCode
    }

    "have a hash code which is not equal to the 6^#2 position's hash code" in {
      position.hashCode must not equalTo (6 ^# 2).hashCode
    }

    "be less than 7^#2 (both position and t-position)" in {
      position < (7 ^# 2) must beTrue
      position < TPosition(7, 2, c) must beTrue
    }

    "be less than 6^#3 (both position and t-position)" in {
      position < (6 ^# 3) must beTrue
      position < TPosition(6, 3, c) must beTrue
    }

    "be greater than 7^#1 (both position and t-position)" in {
      position > (7 ^# 1) must beTrue
      position > TPosition(7, 1, c) must beTrue
    }

    "be less than 6^#2" in {
      position < (6 ^# 2) must beTrue
    }
  }

  "The 7^#0 t-position (w=8,n=5)" should {
    "permit relevant subword max length equal to 0" in {
      TPosition(7, 0, c).relevantSubwordMaxLength must be equalTo 0
    }
  }

  "Every t-position (w=8,n=5)" should {
    "not be an accepting" in {
      var assertions: Result = success
      for {
        i <- 0 to c.w - 2
        e <- 0 to c.n
      } {
        assertions = assertions and (TPosition(i, e, c).isAccepting must beFalse)
      }
      assertions
    }
  }
}
