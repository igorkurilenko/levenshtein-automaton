package io.itdraft.levenshteinautomaton.description

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