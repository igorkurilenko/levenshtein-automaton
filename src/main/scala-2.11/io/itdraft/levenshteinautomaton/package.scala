package io.itdraft

import io.itdraft.levenshteinautomaton.description.parametric.coding.EncodedParametricDescriptionFactory
import io.itdraft.levenshteinautomaton.util.StringUtil

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

package object levenshteinautomaton {
  protected[levenshteinautomaton]
  def createLevenshteinAutomatonConfig(word: String,
                                       degree: Int,
                                       inclTransposition: Boolean = false) =
    new LevenshteinAutomatonConfig(word, degree, inclTransposition)

  implicit class LevenshteinAutomatonConfigExt(config: LevenshteinAutomatonConfig) {
    /**
      * Max boundary of a word the Levenshtein-automaton is built for.
      */
    val w = config.getWordCodePoints.length

    /**
      * Automaton recognizes the set of all words
      * where the Levenshtein-distance between a word from the set
      * and a word the automaton is built for does not exceed `n`.
      */
    val n = config.getDegree

    /**
      * Whether include transposition as a primitive edit operation.
      */
    val inclTransposition = config.doesInclTransposition
  }

}
