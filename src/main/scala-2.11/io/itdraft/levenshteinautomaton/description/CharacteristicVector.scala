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

import scala.collection.mutable.ListBuffer

/**
  * A class to represent the characteristic vector.
  */
protected[levenshteinautomaton] trait CharacteristicVector {

  /**
    * `j&#8712;{1, ..., k}` is the minimal index in the characteristic vector
    * `&lt;b<sub>1</sub>, ..., b<sub>k</sub>&gt;` where `b<sub>j</sub> = 1`.
    *
    * @return a minimal index `j` where `b<sub>j</sub>=1` in the characteristic
    *         vector or `-1` if `b<sub>j</sub>=0` for each `j`.
    */
  def j: Int

  /**
    * Size of the characteristic vector.
    */
  def size: Int
}

/**
  * This class is the default implementation of `CharacteristicVector` where
  * the characteristic vector is represented as a sequence of boolean values.
  */
protected[levenshteinautomaton]
case class DefaultCharacteristicVector(private val v: Seq[Boolean])
  extends CharacteristicVector {

  lazy val j = v.indexOf(true) match {
    case -1 => -1
    case i => i + 1
  }

  lazy val size = v.size
}

protected[levenshteinautomaton] object DefaultCharacteristicVector {

  import io.itdraft.levenshteinautomaton._

  /**
    * Empty characteristic vector.
    */
  val Empty = DefaultCharacteristicVector(ListBuffer.empty)

  /**
    * Creates the characteristic vector `&lt;b<sub>i</sub>, ..., b<sub>k</sub>&gt;`
    * of a symbol specified by a code point with respect to `word` where `i = from` and
    * `k` is the maximal boundary of `word`. If the specified interval is invalid then
    * an empty vector will be returned (no exceptions are thrown).
    *
    * @param symbolCodePoint a symbol's code point the characteristic vector
    *                        is being built for.
    * @param word a word the characteristic vector is being built for.
    * @param from the minimal boundary of `word` to start building the characteristic vector.
    *
    * @return an instance of `DefaultCharacteristicVector` for the `word`'s range from `from`
    *         up to the end of `word`.
    */
  def apply(symbolCodePoint: Int, word: String,
            from: Int): DefaultCharacteristicVector =
    apply(symbolCodePoint, word, from, codePointCount(word))

  /**
    * Creates the characteristic vector `&lt;b<sub>i</sub>, ..., b<sub>k</sub>&gt;`
    * of a symbol specified by a code point with respect to `word` where `i = from` and
    * `k = until - 1`. If the specified interval is invalid then an empty vector will be
    * returned (no exceptions are thrown).
    *
    * @param symbolCodePoint a symbol's code point the characteristic
    *                        vector is being built for.
    * @param word a word the characteristic vector is being built for.
    * @param from minimal boundary of `word` to start building the
    *             characteristic vector.
    * @param until maximal boundary until which the characteristic
    *              vector is being built for.
    *
    * @return an instance of `DefaultCharacteristicVector` for the `word`'s range from `from`
    *         up to (but not including) `until`.
    */
  def apply(symbolCodePoint: Int, word: String,
            from: Int, until: Int): DefaultCharacteristicVector = {
    val v = ListBuffer.empty[Boolean]
    var r, i, curCodePoint = 0

    while (r < until && i < word.length) {
      curCodePoint = word.codePointAt(i)
      if (r >= from && r < until) v.append(curCodePoint == symbolCodePoint)
      i += Character.charCount(curCodePoint)
      r += 1
    }

    DefaultCharacteristicVector(v)
  }
}