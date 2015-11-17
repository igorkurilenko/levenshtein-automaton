package io.itdraft.levenshteinautomaton.description

import io.itdraft.levenshteinautomaton.DefaultAutomatonConfig

package object nonparametric {

  // DSL to create position and t-position

  protected[levenshteinautomaton] implicit class IntExt(val i: Int) {
    def ^#(e: Int)(implicit c: DefaultAutomatonConfig): Position =
      StandardPosition(i, e, c)

    def t = TInt(i)
  }

  protected[levenshteinautomaton] case class TInt(i: Int)

  protected[levenshteinautomaton] implicit class TIntExt(val ti: TInt) {
    def ^#(e: Int)(implicit c: DefaultAutomatonConfig): Position =
      TPosition(ti.i, e, c)
  }
}