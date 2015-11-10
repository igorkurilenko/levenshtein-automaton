package io.itdraft.levenshteinautomaton.description.nonparametric

protected[levenshteinautomaton] sealed trait ImageSet {
  /**
   * Returns a new `ImageSet` which does not contain any position that is subsumed
   * by another element of the image set. A `position` is included in the image set
   * in case it does not already exist and does not subsumed by any position in the
   * image set. Result set does not contain any element subsumes by a `position`.
   */
  def reducedAdd(position: Position): ImageSet

  /**
   * Returns a new `ImageSet` which does not contain any position that is subsumed
   * by another element of the image set. A `position` is included in the image set
   * in case it does not already exist and does not subsumed by any position in the
   * image set. Result set does not contain any element subsumes by a `position`.
   */
  def +~:(position: Position) = reducedAdd(position)

  /**
   * Tests if the `position` subsumed by any element in this `ImageSet`.
   */
  def subsumes(position: Position): Boolean

  /**
   * Tests if the `position` exists in this `ImageSet`
   */
  def contains(position: Position): Boolean

  /**
   * Tests if the `position` exists or subsumed by any element in this `ImageSet`.
   */
  def reducedContains(position: Position) = contains(position) || subsumes(position)

  /**
   * Returns minimal boundary among positions image set includes.
   */
  def minBoundary: Int

  def exists(p: Position => Boolean): Boolean

  def isEmpty: Boolean

  def foreach(f: Position => Unit): Unit

  def fold[B](z: B)(f: (B, Position) => B): B = {
    var acc = z
    foreach { position =>
      acc = f(acc, position)
    }
    acc
  }

  def size: Int

  protected[nonparametric] def accumulate(accumulator: ImageSet)(f: Position => Boolean): ImageSet

  protected[nonparametric] def add(position: Position): ImageSet

  protected[nonparametric] def +(position: Position) = add(position)
}


protected[levenshteinautomaton] object EmptyImageSet extends ImageSet {
  def reducedAdd(position: Position):ImageSet = add(position)

  def subsumes(position: Position) = false

  def minBoundary: Int = throw new NoSuchElementException

  val isEmpty = true

  def foreach(f: (Position) => Unit) = ()

  def contains(position: Position) = false

  def exists(p: (Position) => Boolean) = false

  val size = 0

  protected[nonparametric] def accumulate(accumulator: ImageSet)(f: Position => Boolean) = accumulator

  protected[nonparametric] def add(position: Position) =
    new NonEmptyImageSet(position, EmptyImageSet, EmptyImageSet)

  override def toString = "{}"

  override def equals(obj: scala.Any): Boolean = obj match {
    case other: ImageSet => other.isEmpty
    case _ => false
  }
}


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

  lazy val minBoundary = {
    var min = Integer.MAX_VALUE
    foreach { p =>
      if (p.i < min) min = p.i
    }
    min
  }

  def contains(position: Position) =
    if (position < element) left.contains(position)
    else if (position > element) right.contains(position)
    else true

  def exists(p: (Position) => Boolean) = p(element) || left.exists(p) || right.exists(p)

  val isEmpty = false

  def foreach(f: (Position) => Unit) = {
    f(element)
    left.foreach(f)
    right.foreach(f)
  }

  lazy val size = 1 + left.size + right.size

  override def toString = {
    var sb = new StringBuilder

    sb ++= "{ "
    foreach {
      sb ++= _.toString += ' '
    }
    sb += '}'

    sb.toString()
  }

  override def equals(obj: scala.Any): Boolean = obj match {
    case other: NonEmptyImageSet =>
      var result = true
      foreach(result &= other.contains(_))
      other.foreach(result &= contains(_))
      result
    case _ => false
  }

  override lazy val hashCode = toSet.hashCode()

  private def toSet = fold(Set[Position]())(_ + _)
}

protected[levenshteinautomaton] object ImageSet {
  def apply() = EmptyImageSet

  def apply(positions: Position*) = {
    positions.foldRight(EmptyImageSet: ImageSet)(_ +~: _).asInstanceOf[NonEmptyImageSet]
  }
}