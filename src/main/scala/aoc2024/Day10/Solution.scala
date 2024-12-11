package aoc2024.Day10

import scala.io.Source

class Day10 {
  def run(inputFile: String) =  {
    val startTime = System.nanoTime()
    println(s"Executing for ${inputFile.split("/").last}")
    val input = Source.fromFile(inputFile).getLines().toList

    val output1 = 0
    val output2 = 0

    println(output1)
    println(output2)

    val endTime = System.nanoTime()
    val duration = (endTime - startTime) / 1e9d
    println(s"Runtime: $duration seconds")
  }
}

object Day10 {
  def main(args: Array[String]): Unit = {
    val app = new Day10()

    app.run("./src/main/scala/aoc2024/Day10/Example.txt")
//    app.run("./src/main/scala/aoc2024/Day10/Input.txt")
  }
}