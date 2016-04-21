package io.itdraft.levenshteinautomaton.description.parametric;

public interface ParametricDescription {

    int getInitialStateId();

    /**
     * Tests if a state is a final state.
     *
     * @param stateId an id of a Levenshtein-automaton parametric state to get minimal boundary for.
     * @param w       the word length
     * @return whether a state is a final state or not.
     */
    boolean isStateFinal(int stateId, int w);

    /**
     * Tests if a state is a failure state.
     *
     * @param stateId an id of a Levenshtein-automaton parametric state to get minimal boundary for.
     * @return whether a state is a failure state or not.
     */
    boolean isStateFailure(int stateId);

    /**
     * Returns the minimal boundary of a state.
     *
     * @param stateId an id of a Levenshtein-automaton parametric state to get minimal boundary for.
     * @return the minimal boundary of the state.
     */
    int getStateMinBoundary(int stateId);

    /**
     * Get next parametric state id from the parametric description table.
     * <p>
     * <p>Note: The parametric description table example can be found in the paper: Table 5.2</p>
     *
     * @param stateId              a Levenshtein-automaton parametric state id
     *                             (parametric description table column).
     * @param characteristicVector an encoded as an integer characteristic vector
     *                             of a relevant subword (parametric description table row).
     * @return the id of a next state from the parametric description table.
     */
    int getNextStateId(int characteristicVector, int stateId);

}
