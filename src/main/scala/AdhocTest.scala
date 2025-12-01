object Exercism {
  def transform(scoreMap: Map[Int, Seq[String]]): Map[String, Int] = {
    scoreMap.flatMap {
      case (score, letters) => letters.map(char => (char.toLowerCase(), score))
    }
  }
}

object Test {
  def main(args: Array[String]): Unit = {
    val newScoreMap = Exercism.transform(Map(1 -> Seq("A", "E", "I", "O", "U")))
    println(newScoreMap)
  }
}

