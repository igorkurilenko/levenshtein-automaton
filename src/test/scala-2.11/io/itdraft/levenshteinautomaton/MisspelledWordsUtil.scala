package io.itdraft.levenshteinautomaton

object MisspelledWordsUtil {
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
