package io.itdraft.levenshteinautomaton.description.parametric.coding

object NullEncodedParametricDescriptionFactory extends EncodedParametricDescriptionFactory {
  def getEncodedParametricDescription(degree: Int, inclTransposition: Boolean): EncodedParametricDescription = null
}
