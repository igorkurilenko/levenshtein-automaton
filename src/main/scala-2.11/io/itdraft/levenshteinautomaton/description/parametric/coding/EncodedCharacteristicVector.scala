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
import io.itdraft.levenshteinautomaton.description.parametric.coding.CharacteristicVectorCodec.createEncodedCharacteristicVector

/**
  * This class is the implementation of `CharacteristicVector` where
  * the characteristic vector is encoded as integer value.
  *
  * @note Such way of the characteristic vector representation is
  *       useful for parametric description encoding.
  * @note Maximal allowed size of the characteristic vector is
  *       [[io.itdraft.levenshteinautomaton.description.parametric.coding.CharacteristicVectorCodec#MAX_ALLOWED_SIZE CharacteristicVectorCodec#MAX_ALLOWED_SIZE]].
  *
  * @param asInt a characteristic vector represented as integer value.
  */
protected[levenshteinautomaton]
case class EncodedCharacteristicVector private(asInt: Int) extends CharacteristicVector {

  /**
    * Minimal index `j&#8712;{1, ..., k}` where `b<sub>j</sub>=1` in
    * the characteristic vector `&lt;b<sub>1</sub>, ..., b<sub>k</sub>&gt;`
    * or -1 if `b<sub>j</sub>=0` for each `j`.
    */
  lazy val j = CharacteristicVectorCodec.decodeJ(asInt)

  /**
    * Size of the characteristic vector.
    */
  lazy val size = CharacteristicVectorCodec.size(asInt)
}

protected[levenshteinautomaton]
object EncodedCharacteristicVector {

  /**
    * Encoded empty characteristic vector.
    */
  val Empty = EncodedCharacteristicVector(CharacteristicVectorCodec.EMPTY)

  /**
    * Creates the characteristic vector `&lt;b<sub>i</sub>, ..., b<sub>k</sub>&gt;`
    * of a symbol specified by a code point with respect to `word` where `i = from` and
    * `k = until - 1`. If the specified interval is invalid then an empty vector will be
    * returned (no exceptions are thrown).
    *
    * @note If `until - from` exceeds
    *       `[[io.itdraft.levenshteinautomaton.description.parametric.coding.CharacteristicVectorCodec#MAX_ALLOWED_SIZE CharacteristicVectorCodec#MAX_ALLOWED_SIZE]] + 1`
    *       then invalid characteristic vector will be returned.
    *
    * @param symbolCodePoint a symbol's code point the characteristic
    *                        vector is being built for.
    * @param word a word the characteristic vector is being built for.
    * @param from minimal boundary of `word` to start building the
    *             characteristic vector.
    * @param until maximal boundary until which the characteristic
    *              vector is being built for.
    *
    * @return an instance of `EncodedCharacteristicVector` for the `word`'s range from `from`
    *         up to (but not including) `until`.
    */
  def apply(symbolCodePoint: Int, word: String,
            from: Int, until: Int): EncodedCharacteristicVector =
    EncodedCharacteristicVector(createEncodedCharacteristicVector(symbolCodePoint, word, from, until))
}
