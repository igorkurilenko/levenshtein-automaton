package io.itdraft.levenshteinautomaton.description.parametric.coding

import io.itdraft.levenshteinautomaton.description.CharacteristicVector

protected[levenshteinautomaton]
object EncodedCharacteristicVector {

  val Empty = EncodedCharacteristicVector(CharacteristicVectorCodec.EMPTY)

  def apply(codePoint: Int, word: String,
            from: Int, to: Int): EncodedCharacteristicVector = EncodedCharacteristicVector(
    CharacteristicVectorCodec.createEncoded(codePoint, word, from, to))
}

protected[levenshteinautomaton]
case class EncodedCharacteristicVector(encodedVector: Int) extends CharacteristicVector {
  /**
   * <quote>`j&#8712;{1, ..., k}` is the minimal index in the characteristic vector
   * `&lt;b<sub>1</sub>, ..., b<sub>k</sub>&gt;` where `b<sub>j</sub> = 1`.</quote></p>
   * @return Minimal index `j` where `b<sub>j</sub>=1` in the characteristic vector or
   *         -1 if `b<sub>j</sub>=0` for any `j`.
   */
  lazy val j = CharacteristicVectorCodec.decodeJ(encodedVector)

  /**
   * Returns a size of the characteristic vector `v`.
   */
  lazy val size = CharacteristicVectorCodec.size(encodedVector)
}