package io.itdraft.levenshteinautomaton.description.parametric.coding

object ParametricDescriptionEncoder {

  def main(args: Array[String]) {
    val argsMap = parseArgs(args.toList)
    val degree = argsMap.getOrElse('degree, 2)
    val inclTransposition = argsMap.getOrElse('inclTransposition, false)

    println(s"degree: $degree")
    println(s"inclTransposition: $inclTransposition")
  }

  def parseArgs(argsList: List[String], acc: Map[Symbol, Any] = Map()): Map[Symbol, Any] = {
    argsList match {
      case "--degree" :: value :: tail =>
        parseArgs(tail, acc ++ Map('degree -> value.toInt))
      case "-d" :: value :: tail =>
        parseArgs(tail, acc ++ Map('degree -> value.toInt))
      case "--include-transposition" :: tail =>
        parseArgs(tail, acc ++ Map('inclTransposition -> true))
      case "-t" :: tail =>
        parseArgs(tail, acc ++ Map('inclTransposition -> true))
      case option :: tail =>
        println(s"Unknown option: $option")
        System.exit(1)
        acc
      case _ => acc
    }
  }
}
