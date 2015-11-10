package io.itdraft.levenshteinautomaton.description.nonparametric

import io.itdraft.levenshteinautomaton.DefaultAutomatonConfig
import io.itdraft.levenshteinautomaton.description.State
import org.specs2.mutable.Specification

class FailureStateSpec extends Specification {
  implicit val _ = DefaultAutomatonConfig("10101010", degree = 5)

  "The failure state" should {
    "be failure" in {
      FailureState.isFailure must beTrue
    }

    "be not final" in {
      FailureState.isFinal must beFalse
    }

    "return other state on reduced union with other state" in {
      FailureState.reducedUnion(State(0 ^# 0, 1 ^# 0, 2 ^# 0)) must
        be equalTo State(0 ^# 0, 1 ^# 0, 2 ^# 0)
    }

    "transit to failure state only" in {
      FailureState.transit('x') must be equalTo FailureState
    }

    "be equal to failure state only" >> {
      FailureState != State(0 ^# 0, 1 ^# 0, 2 ^# 0) must beTrue
      FailureState == FailureState must beTrue
    }
  }
}
