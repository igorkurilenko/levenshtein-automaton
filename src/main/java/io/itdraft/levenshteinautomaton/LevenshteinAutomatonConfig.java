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

import static io.itdraft.levenshteinautomaton.util.StringUtil.toCodePoints;

/**
 * Represents a configuration to build the Levenshtein-automaton.
 */
public class LevenshteinAutomatonConfig {
    private int[] wordCodePoints;
    private int degree;
    private boolean inclTransposition;

    /**
     * Constructor. Transposition support is excluded.
     *
     * @param word   an input word the Levenshtein-automaton is for.
     * @param degree automaton recognizes the set of all words
     *               where the Levenshtein-distance between a word from
     *               the set and {@code word} does not exceed {@code degree}.
     */
    public LevenshteinAutomatonConfig(String word, int degree) {
        this(word, degree, false);
    }

    /**
     * Constructor. Transposition support is excluded.
     *
     * @param wordCodePoints code points of an input word the Levenshtein-automaton
     *                       is being built for.
     * @param degree         automaton recognizes the set of all words
     *                       where the Levenshtein-distance between a word from
     *                       the set and {@code word} does not exceed {@code degree}.
     */
    public LevenshteinAutomatonConfig(int[] wordCodePoints, int degree) {
        this(wordCodePoints, degree, false);
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
        this(toCodePoints(word), degree, inclTransposition);
    }

    /**
     * Constructor.
     *
     * @param wordCodePoints    code points of an input word the Levenshtein-automaton
     *                          is being built for.
     * @param degree            automaton recognizes the set of all words
     *                          where the Levenshtein-distance between a word from
     *                          the set and {@code word} does not exceed {@code degree}.
     * @param inclTransposition whether include transposition as a primitive edit
     *                          operation.
     */
    public LevenshteinAutomatonConfig(int[] wordCodePoints, int degree, boolean inclTransposition) {
        this.wordCodePoints = wordCodePoints;
        this.degree = degree;
        this.inclTransposition = inclTransposition;
    }

    /**
     * The input word the Levenshtein-automaton is for.
     */
    public int[] getWordCodePoints() {
        return wordCodePoints;
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

    public void setWord(int[] wordCodePoints) {
        this.wordCodePoints = wordCodePoints;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public void setInclTransposition(boolean inclTransposition) {
        this.inclTransposition = inclTransposition;
    }
}
