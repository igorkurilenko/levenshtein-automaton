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

import io.itdraft.levenshteinautomaton.description._

/**
  * A class to represent the Levenshtein-automaton.
  *
  * @example {{{
  * val dictionaryWord: String = ...
  * val misspelledWord: String = ...
  *
  * val automaton = LevenshteinAutomaton(misspelledWord, degree = 2, includeTransposition = true)
  * var state = automaton.initial
  *
  * // traverse
  * var i, cp = 0
  * while(i < dictionaryWord.length) {
  *   cp = dictionaryWord.codePointAt(i)
  *   state = automaton.next(state, cp)
  *   i += Character.charCount(cp)
  * }
  *
  * if(state.isFinal) println("Misspelled word is accepted.")
  * else println("Misspelled word is rejected.")
  * }}}
  */
class LevenshteinAutomaton protected[levenshteinautomaton](config: AutomatonConfig) {
  private implicit val _ = config

  /**
    * Initial state to start an automaton traverse.
    */
  lazy val initialState = State.initial

  /**
    * Transit to a next state.
    *
    * @param fromState A state to transit from.
    * @param nextSymbolCodePoint Code point of a next symbol from a word
    *                            is being recognized.
    * @return An instance of a next `State`.
    */
  def next(fromState: State, nextSymbolCodePoint: Int) =
    fromState.transit(nextSymbolCodePoint)
}

object LevenshteinAutomaton {

  /**
    * Creates parametrically described (avoids actual computation of next state) or
    * default automaton which is computed at run time.
    *
    * @param word A word to build Levenshtein-automaton for.
    * @param degree Automaton recognizes the set of all words
    *               where the Levenshtein-distance between a word from the set
    *               and a word automaton is built for does not exceed `degree`.
    * @param includeTransposition Include transposition as a primitive edit operation.
    * @return An instance of `LevenshteinAutomaton` which uses `EncodedParametricDescription`
    *         if it exists or computes next states at run time otherwise.
    */
  def apply(word: String, degree: Int, includeTransposition: Boolean): LevenshteinAutomaton =
    apply(AutomatonConfig(word, degree, includeTransposition))

  /**
    * Creates an instance of `LevenshteinAutomaton`.
    */
  def apply(config: AutomatonConfig) =
    new LevenshteinAutomaton(config)
}
