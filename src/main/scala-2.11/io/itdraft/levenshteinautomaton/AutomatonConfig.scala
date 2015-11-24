package io.itdraft.levenshteinautomaton

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

import io.itdraft.levenshteinautomaton.description.parametric.coding.EncodedParametricDescription

/**
  * A trait to represent a configuration to build the Levenshtein-automaton.
  */
sealed trait AutomatonConfig {

  /**
    * Max boundary of a word Levenshtein-automaton is built for.
    */
  lazy val w = word.codePointsCount

  /**
    * Degree of the Levenshtein-automaton.
    */
  lazy val n = degree

  /**
    * A word to build Levenshtein-automaton for.
    */
  def word: String

  /**
    * Automaton recognizes the set of all words
    * where the Levenshtein-distance between a word from the set
    * and a word automaton is built for does not exceed `degree`.
    */
  def degree: Int

  /**
    * Include transposition as a primitive edit operation.
    */
  def inclTransposition: Boolean
}

object AutomatonConfig {

  /**
    * @param word A word to build Levenshtein-automaton for.
    * @param automatonDegree Automaton recognizes the set of all words
    *                        where the Levenshtein-distance between a word from the set
    *                        and a word automaton is built for does not exceed `degree`.
    * @param inclTransposition Include transposition as a primitive edit operation.
    * @return An instance of `AutomatonConfigWithWithEncParametricDescr` if a parametric
    *         description for the specified parameters exists or an instance of
    *         `DefaultAutomatonConfig` otherwise.
    */
  def apply(word: String, automatonDegree: Int, inclTransposition: Boolean) =
    Option(EncodedParametricDescription.get(automatonDegree, inclTransposition)) match {
      case Some(parametricDescription) =>
        AutomatonConfigWithEncodedParametricDescription(word, parametricDescription)
      case None => DefaultAutomatonConfig(word, automatonDegree, inclTransposition)
    }
}

/**
  * A class to represent a configuration for the nonparametrically described
  * Levenshtein-automaton. Use this configuration to create an automaton which
  * is computed at run time.
  *
  * @param word A word to build Levenshtein-automaton for.
  * @param degree Automaton recognizes the set of all words
  *               where the Levenshtein-distance between a word from the set
  *               and a word automaton is built for does not exceed `degree`.
  * @param inclTransposition Include transposition as a primitive edit operation.
  */
case class DefaultAutomatonConfig(word: String,
                                  degree: Int,
                                  inclTransposition: Boolean = false)
  extends AutomatonConfig

/**
  * A class to represent a configuration for the parametrically described
  * Levenshtein-automaton. Using this configuration avoids the actual
  * computation of automaton.
  *
  * @param word A word to build Levenshtein-automaton for.
  * @param encodedParametricDescription Encoded parametric description of the Levenshtein-automaton.
  *                                     Use the `ParametricDescriptionEncoder` app to create one.
  */
case class AutomatonConfigWithEncodedParametricDescription(word: String,
                                                           encodedParametricDescription:
                                                               EncodedParametricDescription)
  extends AutomatonConfig {

  /**
    * Automaton recognizes the set of all words
    * where the Levenshtein-distance between a word from the set
    * and a word automaton is built for does not exceed `degree`.
    */
  val degree = encodedParametricDescription.getAutomatonDegree

  /**
    * Include transposition as a primitive edit operation.
    */
  val inclTransposition = encodedParametricDescription.isInclTransposition
}