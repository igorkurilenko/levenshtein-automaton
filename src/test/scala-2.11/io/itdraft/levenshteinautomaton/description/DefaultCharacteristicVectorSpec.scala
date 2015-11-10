package io.itdraft.levenshteinautomaton.description

import org.specs2.mutable.Specification

class DefaultCharacteristicVectorSpec extends Specification {
  import io.itdraft.levenshteinautomaton._

  "Characteristic vector size" should {
    "be equal to 0 for empty characteristic vector" in {
      DefaultCharacteristicVector.Empty.size must be equalTo 0
    }

    "be equal to 0 if a word is empty but a range is specified" in {
      val word: String = ""
      DefaultCharacteristicVector('x', word, 0, word.codePointsCount).size must be equalTo 0
    }

    "be equal to 0 if a word is not empty but a range is zero" in {
      val word: String = "abcdefg"
      DefaultCharacteristicVector('x', word, 0, 0).size must be equalTo 0
    }

    "be equal to 0 if a word is not empty but a range is out of boundaries" in {
      val word: String = "abcdefg"
      DefaultCharacteristicVector('x', word, 100, 1000).size must be equalTo 0
    }

    """be equal to 7 if a word is the "abcdefg"""" in {
      val word: String = "abcdefg"
      DefaultCharacteristicVector('x', word, 0, word.length).size must be equalTo 7
    }
  }


  """Minimal index j in the characteristic vector of the "hello"""" should {
    val word: String = "hello"

    "be equal to 3 for the 'l' symbol" in {
      DefaultCharacteristicVector('l', word, 0, word.length).j must be equalTo 3
    }

    "be equal to 1 for the 'h' symbol" in {
      DefaultCharacteristicVector('h', word, 0, word.length).j must be equalTo 1
    }

    "be equal to 5 for the 'o' symbol" in {
      DefaultCharacteristicVector('o', word, 0, word.length).j must be equalTo 5
    }

    "be equal to -1 for the 'x' symbol" in {
      DefaultCharacteristicVector('x', word, 0, word.length).j must be equalTo -1
    }
  }
}
