package aoc2024.Day6

import scala.io.Source

class Day6 {
  def run(inputFile: String) =  {
    println(s"Executing for ${inputFile.split("/").last}")
    val input = Source.fromFile(inputFile).getLines().toList

    val output1 = 0
    val output2 = 0

    println(output1)
    println(output2)
  }
}

object Day6 {
  def main(args: Array[String]): Unit = {
    val app = new Day6()
    app.run("./src/main/scala/aoc2024/Day6/Example.txt")
//    app.run("./src/main/scala/aoc2024/Day6/Input.txt")
  }
}