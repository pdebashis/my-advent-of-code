package aoc2024.Day1

import scala.io.Source

class Day1 {
  def run(inputFile: String) =  {
    println(s"Executing for ${inputFile.split("/").last}")
    val input = Source.fromFile(inputFile).getLines().toList
    val (list1, list2) = input.map{ line =>
      val cols = line.split("\\s+").map(_.toInt)
      (cols(0), cols(1))
    }.unzip

    val output1 = list1.sorted.zip(list2.sorted).map{ case (x,y) => Math.abs(x - y)}.sum
    val countTimes = list2.groupBy(identity).map( x => (x._1, x._2.size))
    val output2 = list1.fold(0) { (acc, curr) =>
      val similarity = countTimes.get(curr).map(_ * curr).getOrElse(0)
      acc + similarity
    }

    println(output1)
    println(output2)
  }
}

object Day1 {
  def main(args: Array[String]): Unit = {
    val app = new Day1()
    app.run("./src/main/scala/aoc2024/Day1/Example.txt")
    // app.run("./src/main/scala/aoc2024/Day1/Input.txt")
  }

}