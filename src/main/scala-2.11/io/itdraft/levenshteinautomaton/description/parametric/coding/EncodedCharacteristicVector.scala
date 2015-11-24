package io.itdraft.levenshteinautomaton.description.parametric.coding

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

import io.itdraft.levenshteinautomaton.description.CharacteristicVector
import io.itdraft.levenshteinautomaton.description.parametric.coding.CharacteristicVectorCodec.createEncoded

protected[levenshteinautomaton]
case class EncodedCharacteristicVector(encodedVector: Int) extends CharacteristicVector {

  /**
    * Minimal index `j&#8712;{1, ..., k}` where `b<sub>j</sub>=1` in
    * the characteristic vector `&lt;b<sub>1</sub>, ..., b<sub>k</sub>&gt;`
    * or -1 if `b<sub>j</sub>=0` for any `j`.
    */
  lazy val j = CharacteristicVectorCodec.decodeJ(encodedVector)

  /**
    * Size of the characteristic vector.
    */
  lazy val size = CharacteristicVectorCodec.size(encodedVector)
}

protected[levenshteinautomaton]
object EncodedCharacteristicVector {

  val Empty = EncodedCharacteristicVector(CharacteristicVectorCodec.EMPTY)

  def apply(symbolCodePoint: Int, word: String,
            from: Int, to: Int): EncodedCharacteristicVector =
    EncodedCharacteristicVector(createEncoded(symbolCodePoint, word, from, to))
}
