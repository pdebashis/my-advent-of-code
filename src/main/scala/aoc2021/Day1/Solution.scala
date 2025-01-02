package aoc2021.Day1

import scala.io.Source

class Day1 {
  def run(inputFile: String) :Unit =  {
    val startTime = System.nanoTime()
    println(s"Executing for ${inputFile.split("/").last}")
    val source = scala.io.Source.fromFile(inputFile)
    val input = try{
      source.getLines.toList
    } finally {
      source.close()
    }


    val output1 = input.map(_.toInt).foldLeft((-1, 0)) {
      case ((-1, 0), curr) => (curr, 0)
      case ((prev, count), curr) if prev < curr => (curr, count + 1)
      case ((_, count), curr) => (curr, count)
    }._2

    val sliding_input = input.sliding(3).map(_.map(_.toInt).sum)

    val output2 = sliding_input.foldLeft((-1, 0)){
      case ((-1, 0), curr) => (curr, 0)
      case ((prev, count), curr) if prev < curr => (curr, count + 1)
      case ((_, count), curr) => (curr, count)
    }._2

    println(output1)
    println(output2)

    val endTime = System.nanoTime()
    val duration = (endTime - startTime) / 1e9d
    println(s"Runtime: $duration seconds")
  }
}

object Day1 {
  def main(args: Array[String]): Unit = {
    val app = new Day1()

    app.run("./src/main/scala/aoc2021/Day1/Example.txt")
    app.run("./src/main/scala/aoc2021/Day1/Input.txt")
  }
}