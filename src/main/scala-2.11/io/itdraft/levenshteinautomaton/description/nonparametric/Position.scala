package io.itdraft.levenshteinautomaton.description.nonparametric

import io.itdraft.levenshteinautomaton.LevenshteinAutomatonConfig

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

/**
  * A trait to represent the Levenshtein-automaton state's position.
  */
protected[levenshteinautomaton] sealed trait Position extends Ordered[Position] {

  /**
    * Boundary of an input word.
    */
  def i: Int

  /**
    * Number of edit operations have occurred.
    */
  def e: Int

  /**
    * Whether current `Position` subsumes `position`.
    */
  def subsumes(position: Position): Boolean

  /**
    * Tests if this `Position` is an accepting position.
    */
  def isAccepting: Boolean

  /**
    * Returns the max allowed length of a relevant subword for this `Position`.
    */
  def relevantSubwordMaxLength: Int

  /**
    * Returns the result of comparing this `Position` with `that` `Position`.
    *
    * @note Out of paper. Useful to organize an image set of
    *       positions in the form of a binary search tree.
    */
  def compare(that: Position) = {
    val result = if (e == that.e) i - that.i else e - that.e

    result match {
      case 0 if this.isInstanceOf[StandardPosition] && that.isInstanceOf[TPosition] => 1
      case 0 if this.isInstanceOf[TPosition] && that.isInstanceOf[StandardPosition] => -1
      case _ => result
    }
  }
}

/**
  * A class to represent the standard position (not t-position).
  *
  * @note There is a DSL to create standard position.
  *
  * @example {{{val position = 0 ^# 0}}}
  */
protected[levenshteinautomaton]
case class StandardPosition(i: Int, e: Int, automatonConfig: LevenshteinAutomatonConfig)
  extends Position {

  import io.itdraft.levenshteinautomaton._

  assert(0 <= i && i <= w)
  assert(0 <= e && e <= n)

  private lazy val w = automatonConfig.w
  private lazy val n = automatonConfig.n

  def subsumes(p: Position) = p match {
    case StandardPosition(j, f, _) => e < f && Math.abs(j - i) <= f - e
    case TPosition(j, f, _) => f > e && Math.abs(j - (i - 1)) <= f - e
  }

  lazy val relevantSubwordMaxLength = Math.min(n - e + 1, w - i)

  lazy val isAccepting = w - i <= n - e

  override def hashCode = 41 * (41 + i) + e

  override def toString = s"$i^#$e"
}

/**
  * A class to represent the t-position.
  *
  * @note There is a DSL to create t-position.
  *
  * @example {{{val tPosition = 0.t ^# 0}}}
  */
protected[levenshteinautomaton]
case class TPosition(i: Int, e: Int, automatonConfig: LevenshteinAutomatonConfig)
  extends Position {

  import io.itdraft.levenshteinautomaton._

  assert(0 <= i && i <= w)
  assert(0 <= e && e <= n)

  private lazy val w = automatonConfig.w
  private lazy val n = automatonConfig.n

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
}