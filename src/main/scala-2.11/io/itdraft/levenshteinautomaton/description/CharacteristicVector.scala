package io.itdraft.levenshteinautomaton.description


import scala.collection.mutable.ListBuffer

protected[levenshteinautomaton]
trait CharacteristicVector {
  def j: Int

  def size: Int
}


protected[levenshteinautomaton]
object DefaultCharacteristicVector {

  import io.itdraft.levenshteinautomaton._

  val Empty = DefaultCharacteristicVector(ListBuffer.empty)

  def zerosCharacteristicVector(size: Int) =
    DefaultCharacteristicVector(ListBuffer.fill[Boolean](size){false})

  def apply(codePoint: Int, word: String): DefaultCharacteristicVector =
    apply(codePoint, word, 0)

  def apply(codePoint: Int, word: String,
            from: Int): DefaultCharacteristicVector =
    apply(codePoint, word, from, word.codePointsCount)

  def apply(codePoint: Int, word: String,
            from: Int, until: Int): DefaultCharacteristicVector = {
    val v = ListBuffer.empty[Boolean]
    var r, i, curCodePoint = 0

    while (r < until && i < word.length) {
      curCodePoint = word.codePointAt(i)
      if (r >= from && r < until) v.append(curCodePoint == codePoint)
      i += Character.charCount(curCodePoint)
      r += 1
    }

    DefaultCharacteristicVector(v)
  }
}


protected[levenshteinautomaton]
case class DefaultCharacteristicVector(private val v: Seq[Boolean])
  extends CharacteristicVector {
  lazy val j = v.indexOf(true) match {
    case -1 => -1
    case i => i + 1
  }

  lazy val size = v.size

  def slice(from: Int, to: Int) =
    DefaultCharacteristicVector(v.slice(from, to + 1))
}