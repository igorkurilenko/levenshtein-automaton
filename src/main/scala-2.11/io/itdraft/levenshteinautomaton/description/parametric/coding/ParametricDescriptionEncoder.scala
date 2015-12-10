package io.itdraft.levenshteinautomaton.description.parametric.coding

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

import io.itdraft.levenshteinautomaton._
import io.itdraft.levenshteinautomaton.description.State
import io.itdraft.levenshteinautomaton.description.nonparametric._
import io.itdraft.levenshteinautomaton.description.parametric.coding.util.UIntPackedArray

import scala.collection.immutable.HashSet
import scala.collection.mutable
import scala.collection.mutable.ListBuffer


object ParametricDescriptionEncoder {

  val MAX_DEGREE = (CharacteristicVectorCodec.MAX_ALLOWED_VECTOR_SIZE - 1) / 2
  val DEFAULT_DEGREE = 1
  val DEFAULT_INCL_TRANSPOSITION = false

  def main(args: Array[String]) {
    val (degree, inclTransposition) = parseArgs(args)

    validateDegree(degree)

    println(createEncodedParametricDescription(degree, inclTransposition).toString)
  }

  def parseArgs(args: Array[String]): (Int, Boolean) = {
    val argsMap = parseArgs(args.toList)
    val degree = argsMap.getOrElse('degree, DEFAULT_DEGREE).asInstanceOf[Int]
    val inclTransposition =
      argsMap.getOrElse('inclTransposition, DEFAULT_INCL_TRANSPOSITION).asInstanceOf[Boolean]
    (degree, inclTransposition)
  }

  def parseArgs(argsList: List[String], acc: Map[Symbol, Any] = Map()): Map[Symbol, Any] = {
    argsList match {
      case "-e" :: value :: tail =>
        parseArgs(tail, acc ++ Map('degree -> value.toInt))
      case "-t" :: tail =>
        parseArgs(tail, acc ++ Map('inclTransposition -> true))
      case option :: tail =>
        println(s"Unknown option: $option")
        System.exit(1)
        acc
      case _ => acc
    }
  }

  def validateDegree(degree: Int): Unit = {
    if (degree > MAX_DEGREE) {
      println(s"Current implementation of the parametric " +
        s"description encoding doesn't support the degree of the " +
        s"Levenshtein-automaton greater than $MAX_DEGREE")

      System.exit(1)
    }
  }

  private type CharacteristicVectorAsInt = Int

  /**
    * Parametric description transition table is described by characteristic vector
    * mapped to such transition triplet.
    */
  private type Transition = (StateIdTransitFrom, StateIdTransitTo, TransitionMinBoundaryOffset)

  private type StateId = Int

  /**
    * A state id to perform transition from (for transition table).
    */
  private type StateIdTransitFrom = StateId

  /**
    * A state id to perform transition to (for transition table).
    */
  private type StateIdTransitTo = StateId

  /**
    * Min boundary offset for transition (for transition table).
    */
  private type TransitionMinBoundaryOffset = Int

  /**
    * Computes the parametric description for specified parameters and encodes it.
    *
    * @param degree the degree of the Levenshtein-automaton.
    * @param inclTransposition whether include transposition as a primitive edit operation.
    * @return an instance of `EncodedParametricDescription`.
    */
  def createEncodedParametricDescription(degree: Int, inclTransposition: Boolean) = {
    val maxVectorAsInt = {
      val relevantSubwordMaxLength = 2 * degree + 1
      var result = CharacteristicVectorCodec.EMPTY
      for (_ <- 0 until relevantSubwordMaxLength) {
        result <<= 1
        result |= 1
      }
      result
    }
    /**
      * Mines all possible parametric states (represented as nonparametric
      * states with min boundary equal to 0) and transitions.
      */
    def computeParametricDescription: ParametricDescription = {
      val parametricDescription = new ParametricDescription
      val statesToComputeTransitionsFor = new StatesToComputeTransitionsFor

      do {
        val parametricState = statesToComputeTransitionsFor.next

        for (vectorAsInt <- minVectorAsInt(parametricState) to maxVectorAsInt) {
          val config = createLevenshteinAutomatonConfig(generateAppropriateWord(vectorAsInt),
            degree, inclTransposition)
          val nextState = computeNextState(parametricState.asNonparametricState)(config)

          if (!nextState.isFailure) {
            val parametricNextState = nextState.toPseudoParametric(config)

            // add to compute all possible transitions for freshly state
            if (!parametricDescription.hasParametricState(parametricNextState)) {
              statesToComputeTransitionsFor add parametricNextState
            }

            // add transition to parametric description
            val minBoundaryOffset = nextState.minBoundary
            val stateIdTransitFrom = parametricDescription.addParametricState(parametricState)
            val stateIdTransitTo = parametricDescription.addParametricState(parametricNextState)
            val transition = (stateIdTransitFrom, stateIdTransitTo, minBoundaryOffset)

            parametricDescription.addTransition(vectorAsInt, transition)
          }
        }
      } while (statesToComputeTransitionsFor.nonEmpty)

      parametricDescription
    }

    def encodeParametricDescription(parametricDescription: ParametricDescription) = {
      val (encodedTransitions, encodedBoundaryOffsets) =
        encodeParametricTransitionTable(parametricDescription)
      val encodedEMinusStateLength = encodeEMinusStateLengthAddendums(parametricDescription)

      new EncodedParametricDescription(degree, inclTransposition,
        UIntPackedArray.pack(encodedTransitions),
        UIntPackedArray.pack(encodedBoundaryOffsets),
        encodedEMinusStateLength)
    }

    def encodeParametricTransitionTable(parametricDescription: ParametricDescription): (Array[StateIdTransitFrom], Array[StateIdTransitFrom]) = {
      val statesCount = parametricDescription.parametricStatesCount
      val failureStateId = statesCount
      val encodedTransitions = Array.fill(statesCount * maxVectorAsInt)(failureStateId)
      val encodedBoundaryOffsets = Array.fill(statesCount * maxVectorAsInt)(0)

      for (v <- CharacteristicVectorCodec.EMPTY to maxVectorAsInt) {
        parametricDescription.getTransitions(v).foreach { transition =>
          val (from, to, offset) = transition
          val index = (v - 1) * statesCount + from

          encodedTransitions(index) = to
          encodedBoundaryOffsets(index) = offset
        }
      }

      (encodedTransitions, encodedBoundaryOffsets)
    }

    def encodeEMinusStateLengthAddendums(parametricDescription: ParametricDescription) = {
      val encodedEMinusStateLength = Array.fill(parametricDescription.parametricStatesCount)(0)

      parametricDescription.foreachParametricState { (parametricState, stateId) =>
        val maxPosition = maxBoundaryPosition(parametricState)
        val eMinusStateLength = maxPosition.e - maxPosition.i

        encodedEMinusStateLength(stateId) = eMinusStateLength
      }
      encodedEMinusStateLength
    }

    encodeParametricDescription(computeParametricDescription)
  }

  /**
    * Generate a word which corresponds to a characteristic vector
    * to build the Levenshtein-automaton.
    */
  private def generateAppropriateWord(vectorAsInt: CharacteristicVectorAsInt): String =
    CharacteristicVectorCodec.toBinaryString(vectorAsInt)

  /**
    * Returns characteristic vector appropriate for `state` to start
    * computing all possible transitions.
    */
  private def minVectorAsInt(state: PseudoParametricState) =
    1 << maxBoundaryPosition(state).i

  /**
    * Returns a position which is the maximal boundary in `state`.
    */
  private def maxBoundaryPosition(state: PseudoParametricState) = {
    var max: Option[Position] = None
    state.asNonparametricState.imageSet.foreach { p =>
      max match {
        case Some(m) => if (p.i > m.i) max = Some(p)
        case None => max = Some(p)
      }
    }
    max.get
  }

  /**
    * Returns next state of the Levenshtein-automaton.
    */
  private def computeNextState(state: NonparametricState)
                              (implicit c: LevenshteinAutomatonConfig): NonparametricState = {
    val automaton = LazyLevenshteinAutomaton(c)

    automaton.nextState(adjustConfig(state), '1').asInstanceOf[NonparametricState]
  }

  /**
    * Substitutes config `c` in `state`.
    *
    * @return a clone version of `state` with substituted config `c`.
    */
  private def adjustConfig(state: NonparametricState)
                          (implicit c: LevenshteinAutomatonConfig): NonparametricState = {
    val imageSet = state.imageSet.fold(EmptyImageSet: ImageSet) { (acc, p) =>
      p match {
        case StandardPosition(i, e, _) => (i ^# e) +~: acc
        case TPosition(i, e, _) => (i.t ^# e) +~: acc
      }
    }
    State(imageSet)
  }

  /** Shifts state so as to become state with minimal boundary equal to 0.
    *
    * @return an instance of `NonparametricState` with minimal boundary equal to 0.
    */
  private def toLowerMinBoundary(state: NonparametricState)
                                (implicit c: LevenshteinAutomatonConfig): NonparametricState = {
    val imageSet = state.imageSet.fold(EmptyImageSet: ImageSet) { (acc, p) =>
      p match {
        case StandardPosition(i, e, _) => ((i - state.minBoundary) ^# e) +~: acc
        case TPosition(i, e, _) => ((i - state.minBoundary).t ^# e) +~: acc
      }
    }
    State(imageSet)
  }

  /**
    * Pseudo parametric state meant a state with min boundary equal to 0
    * in current implementation.
    */
  case class PseudoParametricState(asNonparametricState: NonparametricState) {
    if (asNonparametricState.minBoundary != 0) throw new IllegalArgumentException(
      "asNonparametricState must have min boundary equal to 0")
  }

  private implicit class NonparametricStateExt(s: NonparametricState) {
    /**
      * Pseudo parametric state meant a state with min boundary equal to 0
      * in current implementation.
      */
    def toPseudoParametric(implicit c: LevenshteinAutomatonConfig) =
      PseudoParametricState(asNonparametricState = toLowerMinBoundary(s))
  }

  private class ParametricDescription {
    val InitialStateId = 0
    private val parametricStates = ListBuffer.empty[PseudoParametricState]
    private val transitionTable =
      mutable.HashMap.empty[CharacteristicVectorAsInt, HashSet[Transition]]

    /** Adds a pseudo parametric state to the parametric description.
      *
      * @param state a `NonparametricState` with minimal boundary equal to 0
      *              assumed as the parametric state.
      * @return id of the parametric state.
      */
    def addParametricState(state: PseudoParametricState): StateId = {
      val id = parametricStates.indexOf(state)

      if (id == -1) {
        parametricStates += state
        parametricStates.size - 1

      } else id
    }

    /**
      * Adds transition for characteristic vector.
      */
    def addTransition(characteristicVectorAsInt: CharacteristicVectorAsInt,
                      transition: Transition): Unit = {
      if (!transitionTable.contains(characteristicVectorAsInt)) {
        transitionTable(characteristicVectorAsInt) = HashSet.empty[Transition]
      }

      transitionTable(characteristicVectorAsInt) =
        transitionTable(characteristicVectorAsInt) + transition
    }

    def getTransitions(characteristicVectorAsInt: CharacteristicVectorAsInt) =
      transitionTable(characteristicVectorAsInt)

    /** Tests if a pseudo parametric `state` already present in this `ParametricDescription`.
      *
      * @param state a `NonparametricState` with minimal boundary equal to 0
      *              assumed as the parametric state.
      */
    def hasParametricState(state: PseudoParametricState) = parametricStates.contains(state)

    /**
      * Parametric states count excluding failure state.
      */
    def parametricStatesCount = parametricStates.size

    def foreachParametricState(f: (PseudoParametricState, StateId) => Unit) =
      parametricStates.zipWithIndex.foreach { case (s, i) => f(s, i) }
  }

  /**
    * Pseudo parametric states to compute all possible transitions for.
    */
  private class StatesToComputeTransitionsFor {
    private var states = HashSet.empty[PseudoParametricState]

    def next: PseudoParametricState =
      if (states.isEmpty) {
        implicit val _ = createLevenshteinAutomatonConfig("", 0)
        // start parametric states mining with the { 0^#0 } state
        PseudoParametricState(State(0 ^# 0))
      } else {
        val state = states.head
        states = states.tail
        state
      }

    def contains(state: PseudoParametricState) = states.contains(state)

    def add(state: PseudoParametricState) = states = states + state

    def nonEmpty = states.nonEmpty
  }

}