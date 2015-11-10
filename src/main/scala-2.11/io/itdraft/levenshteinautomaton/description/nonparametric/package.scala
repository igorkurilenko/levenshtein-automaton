package io.itdraft.levenshteinautomaton.description

import io.itdraft.levenshteinautomaton.DefaultAutomatonConfig

package object nonparametric {

  protected[levenshteinautomaton] implicit class IntExtension(val i: Int) {
    def ^#(e: Int)(implicit c: DefaultAutomatonConfig): Position =
      StandardPosition(i, e, c)
  }

}
