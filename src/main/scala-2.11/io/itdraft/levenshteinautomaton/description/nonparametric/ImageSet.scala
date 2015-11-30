package io.itdraft.levenshteinautomaton.description.nonparametric

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
  * This class represents an image set of state's positions in the form of
  * a binary search tree.
  */
protected[levenshteinautomaton] sealed trait ImageSet {

  /**
    * Returns a new `ImageSet` which does not contain any position that is subsumed
    * by any position of the image set. A `position` is included in the image set
    * in case it does not already exist and does not subsumed by any position in the
    * image set. Result set does not contain any element subsumes by `position`.
    *
    * @param position a position to add to image set.
    *
    * @return a new `ImageSet` consisting of positions where every position is not
    *         subsumed by any other position.
    */
  def reducedAdd(position: Position): ImageSet

  /**
    * Returns a new `ImageSet` which does not contain any position that is subsumed
    * by any position of the image set. A `position` is included in the image set
    * in case it does not already exist and does not subsumed by any position in the
    * image set. Result set does not contain any position subsumes by `position`.
    *
    * @param position a position to add to image set.
    *
    * @return a new `ImageSet` consisting of positions where every position is not
    *         subsumed by any other position.
    */
  def +~:(position: Position) = reducedAdd(position)

  /**
    * Tests if `position` is subsumed by any position within this `ImageSet`.
    */
  def subsumes(position: Position): Boolean

  /**
    * Tests if `position` exists within this `ImageSet`
    */
  def contains(position: Position): Boolean

  /**
    * Tests if `position` exists or subsumed by any element within this `ImageSet`.
    */
  def reducedContains(position: Position) = contains(position) || subsumes(position)

  /**
    * Returns whether there exists a position within this `ImageSet`
    * that satisfies a predicate `p`.
    */
  def exists(p: Position => Boolean): Boolean

  /**
    * Tests whether this `ImageSet` contains any `Position`.
    */
  def isEmpty: Boolean

  /**
    * This method applies `f` to every position in this `ImageSet`.
    */
  def foreach(f: Position => Unit): Unit

  /**
    * Folds the positions of this `ImageSet` using the specified associative binary operator.
    * The order in which operations are performed on positions is unspecified and may be
    * nondeterministic.
    *
    * @param op a binary operator than must be associative.
    * @param z a neutral element for the fold operation.
    * @tparam B a type parameter for the binary operator.
    *
    * @return the result of applying fold operator `op` between
    *         all positions and `z`.
    */
  def fold[B](z: B)(op: (B, Position) => B): B = {
    var acc = z
    foreach { position =>
      acc = op(acc, position)
    }
    acc
  }

  /**
    * Returns the number of positions in this `ImageSet`.
    */
  def size: Int

  /**
    * Selects all positions of this `ImageSet` which satisfy a predicate `p` and
    * adds them to the `accumulator`.
    *
    * @param accumulator an image set to add selected positions to.
    * @param p a predicate used to test positions.
    *
    * @return a new `ImageSet` consisting of all positions from `accumulator`
    *         and positions from this `ImageSet` which satisfy `p`.
    */
  protected[nonparametric] def accumulate(accumulator: ImageSet)(p: Position => Boolean): ImageSet

  /**
    * Returns a copy of this `ImageSet` with a `position` added
    * if it isn't already within.
    *
    * @param position a position to add.
    *
    * @return a new `ImageSet` containing `position`.
    */
  protected[nonparametric] def add(position: Position): ImageSet

  /**
    * Returns a copy of this `ImageSet` with a `position` added
    * if it isn't already within.
    *
    * @param position a position to add.
    *
    * @return a new `ImageSet` containing `position`.
    */
  protected[nonparametric] def +(position: Position) = add(position)

  /**
    * Compares this `ImageSet` with another object for equality.
    *
    * @param that the other object.
    *
    * @return `true` if `that` is `ImageSet` which contains the same
    *         elements as this `ImageSet` or if both are empty.
    */
  override def equals(that: scala.Any): Boolean = that match {
    case other: ImageSet =>
      var result = isEmpty == other.isEmpty
      if (result) foreach(result &= other.contains(_))
      if (result) other.foreach(result &= contains(_))
      result
    case _ => false
  }

  /**
    * String representation of this `ImageSet`.
    */
  override def toString = {
    var sb = new StringBuilder

    sb ++= "{ "
    foreach {
      sb ++= _.toString += ' '
    }
    sb += '}'

    sb.toString()
  }
}

/**
  * A singleton class to represent the empty image set of state's positions.
  */
protected[levenshteinautomaton] object EmptyImageSet extends ImageSet {

  def reducedAdd(position: Position): ImageSet = add(position)

  def subsumes(position: Position) = false

  /**
    * Tests if it's an empty image set. Always `true` for `EmptyImageSet`.
    */
  val isEmpty = true

  def foreach(f: (Position) => Unit) = ()

  def contains(position: Position) = false

  def exists(p: (Position) => Boolean) = false

  /**
    * The number of positions in image set. Always `0` for `EmptyImageSet`.
    */
  val size = 0

  protected[nonparametric]
  def accumulate(accumulator: ImageSet)(f: Position => Boolean) = accumulator

  protected[nonparametric] def add(position: Position) =
    new NonEmptyImageSet(position, EmptyImageSet, EmptyImageSet)

  override lazy val hashCode = 0
}

/**
  * An immutable class to represent a nonempty image set of state's positions.
  */
protected[levenshteinautomaton]
class NonEmptyImageSet(element: Position, left: ImageSet, right: ImageSet) extends ImageSet {

  def reducedAdd(position: Position): ImageSet =
    if (subsumes(position)) this
    else accumulate(ImageSet(position)) {
      !position.subsumes(_)
    }

  protected[nonparametric] def accumulate(accumulator: ImageSet)(f: Position => Boolean) = {
    if (f(element)) left.accumulate(right.accumulate(accumulator + element)(f))(f)
    else left.accumulate(right.accumulate(accumulator)(f))(f)
  }

  protected[nonparametric] def add(position: Position): ImageSet = {
    if (position < element) new NonEmptyImageSet(element, left + position, right)
    else if (position > element) new NonEmptyImageSet(element, left, right + position)
    else this
  }

  def subsumes(position: Position) =
    element.subsumes(position) || left.subsumes(position) || right.subsumes(position)

  def contains(position: Position) =
    if (position < element) left.contains(position)
    else if (position > element) right.contains(position)
    else true

  def exists(p: (Position) => Boolean) = p(element) || left.exists(p) || right.exists(p)

  /**
    * Tests if it's an empty image set or not. Always `false` for `NonEmptyImageSet`.
    */
  val isEmpty = false

  def foreach(f: (Position) => Unit) = {
    f(element)
    left.foreach(f)
    right.foreach(f)
  }

  /**
    * Returns the number of positions in this `ImageSet`.
    */
  lazy val size = 1 + left.size + right.size

  override lazy val hashCode = toSet.hashCode()

  private def toSet = fold(Set[Position]())(_ + _)
}

protected[levenshteinautomaton] object ImageSet {

  /**
    * The empty image set.
    */
  def apply() = EmptyImageSet

  def apply(positions: Position*) =
    positions.foldRight(EmptyImageSet: ImageSet)(_ +~: _).asInstanceOf[NonEmptyImageSet]
}