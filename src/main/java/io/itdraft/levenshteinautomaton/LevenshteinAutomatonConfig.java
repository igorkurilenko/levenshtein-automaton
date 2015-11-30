package io.itdraft.levenshteinautomaton;

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

import io.itdraft.levenshteinautomaton.description.parametric.coding.DefaultEncodedParametricDescriptionFactory;
import io.itdraft.levenshteinautomaton.description.parametric.coding.EncodedParametricDescription;
import io.itdraft.levenshteinautomaton.description.parametric.coding.EncodedParametricDescriptionFactory;

/**
 * Represents a configuration to build the Levenshtein-automaton.
 */
public class LevenshteinAutomatonConfig {
    private String word;
    private int wordCodePointCount;
    private int degree;
    private boolean inclTransposition;
    private EncodedParametricDescriptionFactory parametricDescriptionFactory;

    /**
     * Constructor. Transposition support is excluded.
     *
     * @param word                         an input word the Levenshtein-automaton is for.
     * @param degree                       automaton recognizes the set of all words
     *                                     where the Levenshtein-distance between a word from
     *                                     the set and {@code word} does not exceed {@code degree}.
     * @param parametricDescriptionFactory a factory to discover the encoded parametric description
     *                                     of the Levenshtein-automaton.
     */
    public LevenshteinAutomatonConfig(String word, int degree,
                                      EncodedParametricDescriptionFactory parametricDescriptionFactory) {
        this(word, degree, false, parametricDescriptionFactory);
    }

    /**
     * Constructor.
     *
     * @param word              an input word the Levenshtein-automaton is for.
     * @param degree            automaton recognizes the set of all words
     *                          where the Levenshtein-distance between a word from
     *                          the set and {@code word} does not exceed {@code degree}.
     * @param inclTransposition whether include transposition as a primitive edit
     *                          operation.
     */
    public LevenshteinAutomatonConfig(String word, int degree, boolean inclTransposition) {
        this(word, degree, inclTransposition, new DefaultEncodedParametricDescriptionFactory());
    }

    /**
     * Constructor.
     *
     * @param word                         an input word the Levenshtein-automaton is for.
     * @param degree                       automaton recognizes the set of all words
     *                                     where the Levenshtein-distance between a word from
     *                                     the set and {@code word} does not exceed {@code degree}.
     * @param inclTransposition            whether include transposition as a primitive edit
     *                                     operation.
     * @param parametricDescriptionFactory a factory to discover the encoded parametric description
     *                                     of the Levenshtein-automaton.
     */
    public LevenshteinAutomatonConfig(String word, int degree, boolean inclTransposition,
                                      EncodedParametricDescriptionFactory parametricDescriptionFactory) {
        this.word = word;
        this.wordCodePointCount = word.codePointCount(0, word.length());
        this.degree = degree;
        this.inclTransposition = inclTransposition;
        this.parametricDescriptionFactory = parametricDescriptionFactory;
    }

    /**
     * The input word the Levenshtein-automaton is for.
     */
    public String getWord() {
        return word;
    }

    /**
     * Automaton recognizes the set of all words
     * where the Levenshtein-distance between a word from the set
     * and a word the automaton is built for does not exceed degree.
     */
    public int getDegree() {
        return degree;
    }

    /**
     * Whether include transposition as a primitive edit operation.
     */
    public boolean doesInclTransposition() {
        return inclTransposition;
    }

    /**
     * A factory to discover the parametric description of the Levenshtein-automaton.
     */
    public EncodedParametricDescriptionFactory getParametricDescriptionFactory() {
        return parametricDescriptionFactory;
    }

    /**
     * Returns the number of Unicode code points of a word
     * the Levenshtein-automaton is for.
     */
    public int getWordCodePointCount() {
        return wordCodePointCount;
    }

    public EncodedParametricDescription getEncodedParametricDescription() {
        return parametricDescriptionFactory == null ? null :
                parametricDescriptionFactory.getEncodedParametricDescription(
                        degree, inclTransposition);
    }
}
