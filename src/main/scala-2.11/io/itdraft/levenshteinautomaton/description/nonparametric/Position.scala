package io.itdraft.levenshteinautomaton.description.nonparametric

import io.itdraft.levenshteinautomaton.DefaultAutomatonConfig

protected[levenshteinautomaton] sealed trait Position extends Ordered[Position] {
  assert(i >= 0)
  assert(e >= 0)

  protected lazy val w = automatonConfig.w
  protected lazy val n = automatonConfig.n

  /**
   * Boundary of an input word.
   */
  def i: Int

  /**
   * Number of edit operations have occurred.
   */
  def e: Int

  def automatonConfig: DefaultAutomatonConfig

  /**
   * Whether current position subsumes a passed position.
   */
  def subsumes(p: Position): Boolean

  /**
   * Tests if this `Position` is an accepting position.
   */
  def isAccepting: Boolean

  /**
   * Returns the max length of a relevant subword for this `Position`.
   */
  def relevantSubwordMaxLength: Int
}


protected[levenshteinautomaton]
case class StandardPosition(i: Int, e: Int,
                            automatonConfig: DefaultAutomatonConfig)
  extends Position {

  def subsumes(p: Position) = p match {
    case StandardPosition(j, f, _) => e < f && Math.abs(j - i) <= f - e
    case TPosition(j, f, _) => f > e && Math.abs(j - (i - 1)) <= f - e
  }

  lazy val relevantSubwordMaxLength = Math.min(n - e + 1, w - i)

  lazy val isAccepting = w - i <= n - e

  override def hashCode = 41 * (41 + i) + e

  override def toString = s"$i^#$e"

  def compare(that: Position) = {
    val result = if (e == that.e) i - that.i else e - that.e

    if (result == 0 && that.isInstanceOf[TPosition]) 1
    else result
  }
}

protected[levenshteinautomaton]
case class TPosition(i: Int, e: Int,
                     automatonConfig: DefaultAutomatonConfig)
  extends Position {

  def subsumes(p: Position) = p match {
    case StandardPosition(j, f, _) => n == f && f > e && i == j
    case TPosition(j, f, _) => f > e && i == j
  }

  val isAccepting = false

  lazy val relevantSubwordMaxLength =
    if (i <= w - 2) Math.min(n - e + 1, w - i)
    else 0


  override def hashCode = 41 * (41 * (41 + i) + e) + 1

  override def toString = s"$i\u2081^#$e"

  def compare(that: Position) = {
    val result = if (e == that.e) i - that.i else e - that.e

    if (result == 0 && that.isInstanceOf[StandardPosition]) -1
    else result
  }
}