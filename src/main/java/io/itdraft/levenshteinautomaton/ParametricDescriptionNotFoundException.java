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

/**
 * Thrown by the {@code create} method of an {@code ParametricLevenshteinAutomaton}
 * to indicate that there isn't a parametric description for the specified parameters.
 */
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
