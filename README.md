# Levenshtein-automaton

[![Build Status](https://travis-ci.org/itdraft/levenshtein-automaton.svg?branch=develop)](https://travis-ci.org/itdraft/levenshtein-automaton)

## Example.scala
```scala
val dictionaryWord: String = ...
val misspelledWord: String = ...

val automaton = LazyLevenshteinAutomaton(
    misspelledWord,
    degree = 2,
    includeTransposition = true)
var state = automaton.initialState

// traverse
var i, cp = 0
while(i < dictionaryWord.length) {
    cp = dictionaryWord.codePointAt(i)
    state = automaton.nextState(state, cp)
    i += Character.charCount(cp)
}

if(state.isFinal) println("Misspelled word is accepted.")
else println("Misspelled word is rejected.")
```

# Reference

* Fast String Correction with Levenshtein-Automata (2002) by Klaus Schulz , Stoyan Mihov 
  (http://bit.ly/1kmvY8x)
