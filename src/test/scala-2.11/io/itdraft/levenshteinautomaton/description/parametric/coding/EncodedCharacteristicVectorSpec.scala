package io.itdraft.levenshteinautomaton.description.parametric.coding

import org.specs2.mutable.Specification

class EncodedCharacteristicVectorSpec extends Specification {
   import io.itdraft.levenshteinautomaton._

   "Size" should {
     "be equal to 0 for empty characteristic vector" in {
       EncodedCharacteristicVector.Empty.size must be equalTo 0
     }

     "be equal to 0 if a word is empty but a range is specified" in {
       val word: String = ""
       EncodedCharacteristicVector('x', word, 0, word.codePointsCount).size must be equalTo 0
     }

     "be equal to 0 if a word is not empty but a range is zero" in {
       val word: String = "abcdefg"
       EncodedCharacteristicVector('x', word, 0, 0).size must be equalTo 0
     }

     "be equal to 0 if a word is not empty but a range is out of boundaries" in {
       val word: String = "abcdefg"
       EncodedCharacteristicVector('x', word, 100, 1000).size must be equalTo 0
     }

     """be equal to 7 if a word is the "abcdefg"""" in {
       val word: String = "abcdefg"
       EncodedCharacteristicVector('x', word, 0, word.length).size must be equalTo 7
     }
   }


   """Minimal index j in the characteristic vector of the "hello"""" should {
     val word: String = "hello"

     "be equal to 3 for symbol 'l'" in {
       EncodedCharacteristicVector('l', word, 0, word.length).j must be equalTo 3
     }

     "be equal to 1 for symbol 'h'" in {
       EncodedCharacteristicVector('h', word, 0, word.length).j must be equalTo 1
     }

     "be equal to 5 for symbol 'o'" in {
       EncodedCharacteristicVector('o', word, 0, word.length).j must be equalTo 5
     }

     "be equal to -1 for symbol 'x'" in {
       EncodedCharacteristicVector('x', word, 0, word.length).j must be equalTo -1
     }
   }
 }
