package io.itdraft.levenshteinautomaton.description.parametric.coding

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
