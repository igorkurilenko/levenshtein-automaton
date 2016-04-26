package io.itdraft.levenshteinautomaton

/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import io.itdraft.levenshteinautomaton.description.parametric.ParametricDescriptionNotFoundException
import org.specs2.matcher.Matcher
import org.specs2.mutable.Specification
import org.specs2.specification.Tables

class ParametricLevenshteinAutomatonSpec extends Specification with Tables {

  import MisspelledWordsUtil._

  "Parametric Levenshtein automaton" should {
    "accept acceptable misspelled words" in {
      val rows = generateRowsForAcceptableMisspelledWords

      Table4("correct" :: "misspelled" :: "degree" :: "inclTranspositions" :: Nil, rows) |> {
        (correct, misspelled, degree, inclTransp) =>
            val config = createLevenshteinAutomatonConfig(correct, degree, inclTransp)
            ParametricLevenshteinAutomaton.create(config) must accept(misspelled)
      }
    }

    "reject not acceptable misspelled words" in {
      "correct" | "misspelled" | "degree" | "inclTranspositions" |>
        "abcdefg" ! "abcdefgxx" ! 1 ! true |
        "abcdefg" ! "abcdefgxx" ! 1 ! false |
        "abcdefg" ! "abcde" ! 1 ! true |
        "abcdefg" ! "abcde" ! 1 ! false |
        "abcdefg" ! "abcdefgxxx" ! 2 ! true |
        "abcdefg" ! "abcdefgxxx" ! 2 ! false |
        "abcdefg" ! "abcd" ! 2 ! true |
        "abcdefg" ! "abcd" ! 2 ! false |  {
        (correct, misspelled, degree, inclTransp) =>
          val config = createLevenshteinAutomatonConfig(correct, degree, inclTransp)
          ParametricLevenshteinAutomaton.create(config) must notAccept(misspelled)
      }
    }

    "throw ParametricDescriptionNotFoundException when " +
      "it's being created and parametric description is not found" in {
      val config = createLevenshteinAutomatonConfig("any", 3, true)
      ParametricLevenshteinAutomaton.create(config) must throwA[ParametricDescriptionNotFoundException]
    }
  }

  def process(automaton: ParametricLevenshteinAutomaton, misspelled: String) = {
    var stateId = automaton.getInitialStateId()
    for (x <- misspelled) stateId = automaton.getNextStateId(stateId, x)
    stateId
  }

  def notAccept(misspelled: String): Matcher[ParametricLevenshteinAutomaton] = {
    automaton: ParametricLevenshteinAutomaton =>
      val stateId = process(automaton, misspelled)
      (!automaton.isFinalState(stateId),
        s"Parametric Levenshtein automaton must not accept a misspelled word")
  }

  def accept(misspelled: String): Matcher[ParametricLevenshteinAutomaton] = {
    automaton: ParametricLevenshteinAutomaton =>
      val stateId = process(automaton, misspelled)
      (automaton.isFinalState(stateId),
        s"Parametric Levenshtein automaton must accept a misspelled word")
  }

  def generateRowsForAcceptableMisspelledWords = {
    var rows: List[DataRow4[String, String, Int, Boolean]] = Nil

    for (degree <- 1 to 2) {
      val correct = "abcdefg"
      var misspelledWords =
        generateMisspelledWords(correct, degree)(forInsertionEditOp) :::
          generateMisspelledWords(correct, degree)(forDeletionEditOp) :::
          generateMisspelledWords(correct, degree)(forSubstitutionEditOp)

      if (degree == 2) {
        misspelledWords = misspelledWords :::
          generateMisspelledWords(correct, 1)(forInsertionEditOp).flatMap {
            generateMisspelledWords(_, 1)(forDeletionEditOp)
          }

        misspelledWords = misspelledWords :::
          generateMisspelledWords(correct, 1)(forInsertionEditOp).flatMap {
            generateMisspelledWords(_, 1)(forSubstitutionEditOp)
          }

        misspelledWords = misspelledWords :::
          generateMisspelledWords(correct, 1)(forDeletionEditOp).flatMap {
            generateMisspelledWords(_, 1)(forSubstitutionEditOp)
          }
      }

      for (inclTranspositions <- List(false, true)) {
        if (inclTranspositions) {
          misspelledWords = misspelledWords :::
            generateMisspelledWords(correct, degree)(forTranspositionEditOp)
        }

        misspelledWords.foreach { misspelled =>
          rows = DataRow4(correct, misspelled, degree, inclTranspositions) :: rows
        }

        rows = DataRow4("", "x" * degree, degree, inclTranspositions) :: rows
      }
    }

    rows
  }
}
