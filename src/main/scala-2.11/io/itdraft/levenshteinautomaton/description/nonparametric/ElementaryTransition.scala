package io.itdraft.levenshteinautomaton.description.nonparametric

import io.itdraft.levenshteinautomaton.DefaultAutomatonConfig
import io.itdraft.levenshteinautomaton.description.{DefaultCharacteristicVector, State}

protected[levenshteinautomaton]
object ElementaryTransition {

  def apply()(implicit automatonCharacteristic: DefaultAutomatonConfig):
  (Position, DefaultCharacteristicVector) => NonParametricState = {
    if (automatonCharacteristic.inclTranspositions) inclTranspositionsTransition
    else standardTransition
  }

  /**
   *
   */
  private def standardTransition(position: Position, v: DefaultCharacteristicVector)
                                (implicit cfg: DefaultAutomatonConfig) = {
    val e = position.e
    val n = cfg.degree

    if (0 <= e && e <= n - 1) standardTransitionPart1(position, v)
    else if (e == n) standardTransitionPart2(position, v)
    else FailureState
  }

  /**
   * Part 1 of the elementary transitions table (Table 4.1)
   */
  private def standardTransitionPart1(position: Position, v: DefaultCharacteristicVector)
                                     (implicit ch: DefaultAutomatonConfig) = {
    val i = position.i
    val e = position.e
    val w = ch.w

    if (i <= w - 2)
      v.j match {
        case -1 => State(i ^# (e + 1), (i + 1) ^# (e + 1))
        case 1 => State((i + 1) ^# e)
        case j => State(i ^# (e + 1), (i + 1) ^# (e + 1), (i + j) ^# (e + j - 1))
      }
    else if (i == w - 1)
      v.j match {
        case -1 => State(i ^# (e + 1), (i + 1) ^# (e + 1))
        case 1 => State((i + 1) ^# e)
      }
    else if (i == w)
      State(w ^# (e + 1))
    else FailureState
  }

  /**
   * Part 2 of the elementary transitions table (Table 4.1)
   */
  private def standardTransitionPart2(position: Position, v: DefaultCharacteristicVector)
                                     (implicit ch: DefaultAutomatonConfig) = {
    val i = position.i
    val w = ch.w
    val n = ch.degree

    if (i <= w - 1)
      v.j match {
        case 1 => State((i + 1) ^# n)
        case _ => FailureState
      }
    else FailureState
  }

  /**
   *
   */
  private def inclTranspositionsTransition(position: Position, v: DefaultCharacteristicVector)
                                          (implicit cfg: DefaultAutomatonConfig) = {
    val e = position.e
    val n = cfg.degree

    if (e == 0 && e < n) inclTranspositionsTransitionPart1(position, v)
    else if (1 <= e && e <= n - 1) inclTranspositionsTransitionPart2(position, v)
    else if (e == n) inclTranspositionsTransitionPart3(position, v)
    else FailureState
  }

  private def inclTranspositionsTransitionPart1(position: Position, v: DefaultCharacteristicVector)
                                               (implicit cfg: DefaultAutomatonConfig) = {
    val i = position.i
    val w = cfg.w

    if (i <= w - 2)
      v.j match {
        case -1 => State(i ^# 1, (i + 1) ^# 1)
        case 1 => State((i + 1) ^# 0)
        case 2 =>
          State(i ^# 1, TPosition(i, 1, cfg), (i + 1) ^# 1, (i + 2) ^# 1)
        case j => State(i ^# 1, (i + 1) ^# 1, (i + j) ^# (j - 1))
      }
    else if (i == w - 1)
      v.j match {
        case 1 => State((i + 1) ^# 0)
        case -1 => State(i ^# 1, (i + 1) ^# 1)
      }
    else if (i == w) State(w ^# 1)
    else FailureState
  }

  private def inclTranspositionsTransitionPart2(position: Position, v: DefaultCharacteristicVector)
                                               (implicit cfg: DefaultAutomatonConfig) = {
    val i = position.i
    val e = position.e
    val w = cfg.w

    if (i <= w - 2)
      position match {
        case StandardPosition(_, _, _) =>
          v.j match {
            case -1 => State(i ^# (e + 1), (i + 1) ^# (e + 1))
            case 1 => State((i + 1) ^# e)
            case 2 => State(i ^# (e + 1), TPosition(i, e + 1, cfg), (i + 1) ^# (e + 1),
              (i + 2) ^# (e + 1))
            case j => State(i ^# (e + 1), (i + 1) ^# (e + 1), (i + j) ^# (e + j - 1))
          }
        case TPosition(_, _, _) =>
          v.j match {
            case 1 => State((i + 2) ^# e)
            case _ => FailureState
          }
      }
    else if (i == w - 1)
      v.j match {
        case 1 => State((i + 1) ^# e)
        case -1 => State(i ^# (e + 1), (i + 1) ^# (e + 1))
      }
    else if (i == w) State(w ^# (e + 1))
    else FailureState
  }

  private def inclTranspositionsTransitionPart3(position: Position, v: DefaultCharacteristicVector)
                                               (implicit cfg: DefaultAutomatonConfig) = {
    val i = position.i
    val w = cfg.w
    val n = cfg.degree

    position match {
      case StandardPosition(_, _, _) =>
        if (i <= w - 1)
          v.j match {
            case 1 => State((i + 1) ^# n)
            case -1 => FailureState
          }
        else FailureState
      case TPosition(_, _, _) =>
        if (i <= w - 2)
          v.j match {
            case 1 => State((i + 2) ^# n)
            case -1 => FailureState
          }
        else FailureState
    }
  }
}