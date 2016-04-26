package io.itdraft.levenshteinautomaton.description.parametric;

/**
 * Represents a factory to get the parametric description
 * of the Levenshtein-automaton
 */
public interface ParametricDescriptionFactory {

    /**
     * Returns parametric description of the Levenshtein-automaton
     * for the specified parameters.
     *
     * @param degree            a degree of the Levenshtein-automaton.
     * @param inclTransposition whether the parametric description is for the
     *                          Levenshtein-automaton which supports transposition
     *                          as a primitive edit operation.
     */
    ParametricDescription getParametricDescription(int degree, boolean inclTransposition);

}
