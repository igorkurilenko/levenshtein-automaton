package io.itdraft.levenshteinautomaton.description.nonparametric

import io.itdraft.levenshteinautomaton.DefaultAutomatonConfig
import org.specs2.execute.Result
import org.specs2.mutable.Specification

class TPositionSpec extends Specification {

  implicit val c = DefaultAutomatonConfig("10101010", degree = 5)

  "The 6^#2 t-position (w=8,n=5)" should {
    val position: Position = 6.t ^# 2

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
      position.subsumes(6.t ^# 3) must beTrue
      position.subsumes(6.t ^# 4) must beTrue
      position.subsumes(6.t ^# 5) must beTrue
    }

    "not subsume 6^#2, 5^#4, 7^#5 t-positions" in {
      position.subsumes(6.t ^# 2) must beFalse
      position.subsumes(5.t ^# 4) must beFalse
      position.subsumes(7.t ^# 5) must beFalse
    }

    "permit relevant subword max length equal to 2" in {
      position.relevantSubwordMaxLength must be equalTo 2
    }

    "have a hash code which is equal to the 6^#2 t-position's hash code" in {
      position.hashCode must be equalTo (6.t ^# 2).hashCode
    }

    "have a hash code which is not equal to the 6^#2 position's hash code" in {
      position.hashCode must not equalTo (6 ^# 2).hashCode
    }

    "be less than 7^#2 (both position and t-position)" in {
      position < (7 ^# 2) must beTrue
      position < (7.t ^# 2) must beTrue
    }

    "be less than 6^#3 (both position and t-position)" in {
      position < (6 ^# 3) must beTrue
      position < (6.t ^# 3) must beTrue
    }

    "be greater than 7^#1 (both position and t-position)" in {
      position > (7 ^# 1) must beTrue
      position > (7.t ^# 1) must beTrue
    }

    "be less than 6^#2" in {
      position < (6 ^# 2) must beTrue
    }
  }

  "The 7^#0 t-position (w=8,n=5)" should {
    "permit relevant subword max length equal to 0" in {
      (7.t ^# 0).relevantSubwordMaxLength must be equalTo 0
    }
  }

  "Every t-position (w=8,n=5)" should {
    "not be an accepting" in {
      var assertions: Result = success
      for {
        i <- 0 to c.w - 2
        e <- 0 to c.n
      } {
        assertions = assertions and ((i.t ^# e).isAccepting must beFalse)
      }
      assertions
    }
  }
}
