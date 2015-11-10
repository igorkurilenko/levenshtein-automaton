package io.itdraft.levenshteinautomaton;

public class ParametricDescriptionNotFoundException extends LevenshteinAutomatonException {

    private static final String MESSAGE =
            "Parametric description was not found for specified parameters: " +
                    "automaton degree is %d, including transpositions is %s";

    public ParametricDescriptionNotFoundException(String message) {
        super(message);
    }

    public static void throwFor(
            int degree, boolean inclTranspositions) throws ParametricDescriptionNotFoundException {
        throw new ParametricDescriptionNotFoundException(
                String.format(MESSAGE, degree, inclTranspositions));
    }
}
