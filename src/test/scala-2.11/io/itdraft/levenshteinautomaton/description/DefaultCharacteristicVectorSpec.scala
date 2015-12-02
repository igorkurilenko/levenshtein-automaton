package io.itdraft.levenshteinautomaton.description

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

import org.specs2.mutable.Specification

class DefaultCharacteristicVectorSpec extends Specification {
  import io.itdraft.levenshteinautomaton._

  "Characteristic vector size" should {
    "be equal to 0 for empty characteristic vector" in {
      DefaultCharacteristicVector.Empty.size must be equalTo 0
    }

    "be equal to 0 if a word is empty but a range is specified" in {
      val word = ""
      DefaultCharacteristicVector('x', word, 0, codePointCount(word)).size must be equalTo 0
    }

    "be equal to 0 if a word is not empty but a range is zero" in {
      val word = "abcdefg"
      DefaultCharacteristicVector('x', word, 0, 0).size must be equalTo 0
    }

    "be equal to 0 if a word is not empty but a range is out of boundaries" in {
      val word = "abcdefg"
      DefaultCharacteristicVector('x', word, 100, 1000).size must be equalTo 0
    }

    """be equal to 7 if a word is the "abcdefg"""" in {
      val word = "abcdefg"
      DefaultCharacteristicVector('x', word, 0, word.length).size must be equalTo 7
    }
  }


  """Minimal index j in the characteristic vector of the "hello"""" should {
    val word = "hello"

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
