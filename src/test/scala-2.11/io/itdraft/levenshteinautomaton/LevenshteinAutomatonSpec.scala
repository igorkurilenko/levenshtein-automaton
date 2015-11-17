package io.itdraft.levenshteinautomaton

import io.itdraft.levenshteinautomaton.description.parametric.coding.EncodedParametricDescription
import org.specs2.matcher.Matcher
import org.specs2.mutable.Specification
import org.specs2.specification.Tables

class LevenshteinAutomatonSpec extends Specification with Tables {

  import Util._

  "Levenshtein automaton" should {
    "accept acceptable misspelled words" in {
      val rows = generateRowsForAcceptableMisspelledWords

      Table5("correct" :: "misspelled" :: "degree" :: "inclTranspositions"
        :: "useParametricDescription" :: Nil, rows) |> {
        (correct, misspelled, degree, inclTransp, useParamDescr) =>
          Automaton(correct, degree, inclTransp, useParamDescr) must accept(misspelled)
      }
    }

    "reject not acceptable misspelled words" in {
      "correct" | "misspelled" | "degree" | "inclTranspositions" | "useParametricDescription" |>
        "abcdefg" ! "abcdefgx" ! 0 ! true ! false |
        "abcdefg" ! "abcdefgx" ! 0 ! false ! false |
        "abcdefg" ! "abcdef" ! 0 ! true ! false |
        "abcdefg" ! "abcdef" ! 0 ! false ! false |
        "abcdefg" ! "abcdefgxx" ! 1 ! true ! false |
        "abcdefg" ! "abcdefgxx" ! 1 ! false ! false |
        "abcdefg" ! "abcdefgxx" ! 1 ! true ! true |
        "abcdefg" ! "abcdefgxx" ! 1 ! false ! true |
        "abcdefg" ! "abcde" ! 1 ! true ! false |
        "abcdefg" ! "abcde" ! 1 ! false ! false |
        "abcdefg" ! "abcde" ! 1 ! true ! true |
        "abcdefg" ! "abcde" ! 1 ! false ! true |
        "abcdefg" ! "abcdefgxxx" ! 2 ! true ! false |
        "abcdefg" ! "abcdefgxxx" ! 2 ! false ! false |
        "abcdefg" ! "abcdefgxxx" ! 2 ! true ! true |
        "abcdefg" ! "abcdefgxxx" ! 2 ! false ! true |
        "abcdefg" ! "abcd" ! 2 ! true ! false |
        "abcdefg" ! "abcd" ! 2 ! false ! false |
        "abcdefg" ! "abcd" ! 2 ! true ! true |
        "abcdefg" ! "abcd" ! 2 ! false ! true |
        "ab" * 15 ! ("ab" * 15).takeRight(14) ! 15 ! true ! false |
        "ab" * 15 ! ("ab" * 15) + ("d" * 16) ! 15 ! true ! false |
        "ab" * 15 ! ("ac" * 14) + "cc" ! 15 ! true ! false |
        "ab" * 15 ! ("ab" * 15).takeRight(14) ! 15 ! false ! false |
        "ab" * 15 ! ("ab" * 15) + ("d" * 16) ! 15 ! false ! false |
        "ab" * 15 ! ("ac" * 14) + "cc" ! 15 ! false ! false | {
        (correct, misspelled, degree, inclTransp, useParamDescr) =>
          Automaton(correct, degree, inclTransp, useParamDescr) must notAccept(misspelled)
      }
    }
  }

  def accept(misspelled: String): Matcher[LevenshteinAutomaton] = {
    automaton: LevenshteinAutomaton =>
      val state = process(automaton, misspelled)
      (state.isFinal, s"Levenshtein automaton must accept a misspelled word")
  }

  def notAccept(misspelled: String): Matcher[LevenshteinAutomaton] = {
    automaton: LevenshteinAutomaton =>
      val state = process(automaton, misspelled)
      (!state.isFinal, s"Levenshtein automaton must not accept a misspelled word")
  }

  def process(automaton: LevenshteinAutomaton, misspelled: String) = {
    var state = automaton.initialState
    for (x <- misspelled) state = automaton.next(state, x)
    state
  }

  object Automaton {
    def apply(correct: String, degree: Int, inclTranspositions: Boolean,
              useParametricDescription: Boolean): LevenshteinAutomaton = {
      val automatonConfig =
        if (useParametricDescription) AutomatonConfigWithWithEncodedParametricDescription(
          correct, Option(EncodedParametricDescription.get(degree, inclTranspositions)).get)
        else DefaultAutomatonConfig(correct, degree, inclTranspositions)

      LevenshteinAutomaton(automatonConfig)
    }
  }

  def generateRowsForAcceptableMisspelledWords = {
    var rows: List[DataRow5[String, String, Int, Boolean, Boolean]] = Nil

    // if degree is 0
    for (inclTranspositions <- List(false, true)) {
      val degree = 0
      val correct = "abcdefg"
      val misspelled = correct + ("x" * degree)
      rows = DataRow5(correct, misspelled, degree, inclTranspositions, false) :: rows
    }

    // if degree is 1 or 2
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

        for (useParametricDescription <- List(false, true)) {
          misspelledWords.foreach { misspelled =>
            rows = DataRow5(correct, misspelled, degree,
              inclTranspositions, useParametricDescription) :: rows
          }

          rows = DataRow5("", "x" * degree, degree,
            inclTranspositions, useParametricDescription) :: rows
        }
      }
    }

    // if degree is 15
    for (inclTranspositions <- List(false, true)) {
      val degree = 15
      val correct = "ab" * degree
      var misspelledWords =
        correct.takeRight(degree) :: // for insertion edit op
          correct + ("d" * degree) :: // for deletion edit op
          "ac" * degree :: // for substitution edit op
          Nil
      // for transposition edit op
      if (inclTranspositions) misspelledWords = "ba" * degree :: misspelledWords

      misspelledWords.foreach { misspelled =>
        rows = DataRow5(correct, misspelled, degree, inclTranspositions, false) :: rows
      }
    }
    rows
  }

  object Util {
    def generateMisspelledWords(correct: String, degree: Int)
                               (misspell: (String, List[Boolean]) =>
                                 Option[String]): List[String] = {
      var result: List[String] = Nil
      createCombinationMatrix(correct.length, degree).foreach { errorMarks =>
        misspell(correct, errorMarks).foreach { misspelled =>
          result = misspelled :: result
        }
      }
      result
    }

    def forInsertionEditOp(correct: String, errorMarks: List[Boolean]): Option[String] = {
      assert(correct.length == errorMarks.size)
      var donor = correct
      val misspelled = StringBuilder.newBuilder

      errorMarks.foreach { here =>
        val ch = donor.charAt(0)
        if (!here) misspelled.append(ch)
        donor = donor.substring(1)
      }
      Some(misspelled.toString())
    }

    def forDeletionEditOp(correct: String, errorMarks: List[Boolean]): Option[String] = {
      assert(correct.length == errorMarks.size)
      var donor = correct
      val misspelled = StringBuilder.newBuilder

      errorMarks.foreach { here =>
        val ch = donor.charAt(0)
        if (here) misspelled.append("x")
        misspelled.append(ch)
        donor = donor.substring(1)
      }
      Some(misspelled.toString())
    }

    def forSubstitutionEditOp(correct: String, errorMarks: List[Boolean]): Option[String] = {
      assert(correct.length == errorMarks.size)
      var donor = correct
      val misspelled = StringBuilder.newBuilder

      errorMarks.foreach { here =>
        val ch = donor.charAt(0)
        if (here) misspelled.append("x")
        else misspelled.append(ch)
        donor = donor.substring(1)
      }
      Some(misspelled.toString())
    }

    def forTranspositionEditOp(correct: String, errorMarks: List[Boolean]): Option[String] = {
      assert(correct.length == errorMarks.size)
      var donor = correct
      val misspelled = StringBuilder.newBuilder
      var prevCh, curCh = -1
      var transpositionsRequired = 0
      var transpositionsHappened = 0

      errorMarks.foreach { here =>
        if (prevCh != -1) misspelled.append(prevCh.asInstanceOf[Char])
        prevCh = curCh
        curCh = donor.charAt(0)
        donor = donor.substring(1)
        if (here) {
          transpositionsRequired += 1
          if (prevCh != -1) {
            val tmp = prevCh
            prevCh = curCh
            curCh = tmp
            transpositionsHappened += 1
          }
        }
      }

      misspelled.append(prevCh.asInstanceOf[Char])
      misspelled.append(curCh.asInstanceOf[Char])

      if (transpositionsHappened == transpositionsRequired) Some(misspelled.toString())
      else None
    }

    def createCombinationMatrix(size: Int, limit: Int): List[List[Boolean]] = {
      var result: List[List[Boolean]] = Nil
      val totalNumCombinations: Int = Math.pow(2, size).toInt

      for (i <- 0 until totalNumCombinations) {
        var counter: Int = 0
        var combination: List[Boolean] = Nil

        for (j <- 0 until size) {
          val currentValue = totalNumCombinations * j + i
          val ret = 1 & (currentValue >>> j)
          val value = ret != 0

          combination = value :: combination

          if (value) counter += 1
        }

        if (counter > 0 && counter <= limit) {
          result = combination :: result
        }
      }

      result
    }
  }

}