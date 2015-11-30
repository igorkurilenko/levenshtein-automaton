package io.itdraft.levenshteinautomaton.description.parametric.coding;

/**
 * Represents a factory to get the encoded parametric description
 * of the Levenshtein-automaton
 */
public interface EncodedParametricDescriptionFactory {

    /**
     * Returns encoded parametric description of the Levenshtein-automaton
     * for the specified parameters.
     *
     * @param degree            a degree of the Levenshtein-automaton.
     * @param inclTransposition whether the parametric description is for the
     *                          Levenshtein-automaton which supports transposition
     *                          as a primitive edit operation.
     */
    EncodedParametricDescription getEncodedParametricDescription(
            int degree, boolean inclTransposition);

}
