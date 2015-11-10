package io.itdraft.levenshteinautomaton.description.nonparametric

import io.itdraft.levenshteinautomaton.DefaultAutomatonConfig
import io.itdraft.levenshteinautomaton.description.State
import org.specs2.mutable.Specification

class FailureStateSpec extends Specification {

  "is failure" >> {
    FailureState.isFailure must beTrue
  }

  "is not final" >> {
    FailureState.isFinal must beFalse
  }

  "reducedUnion with other state" should {
    "return other state" in {
      implicit val automatonConfig = DefaultAutomatonConfig("10101010", degree = 5)

      FailureState.reducedUnion(State(0 ^# 0, 1 ^# 0, 2 ^# 0)) must
        be equalTo State(0 ^# 0, 1 ^# 0, 2 ^# 0)
    }
  }

  "transit to failure state only" >> {
    FailureState.transit('x') must be equalTo FailureState
  }
}
