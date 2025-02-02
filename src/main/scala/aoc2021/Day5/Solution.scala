package aoc2021.Day5

import scala.io.Source
import scala.util.matching.Regex

class Day5 {
  def run(inputFile: String) :Unit =  {
    val startTime = System.nanoTime()
    println(s"Executing for ${inputFile.split("/").last}")
    val source = scala.io.Source.fromFile(inputFile)
    val input = try{
      source.getLines.toList
    } finally {
      source.close()
    }

    val line_format = """(\d+),(\d+) -> (\d+),(\d+)""".r
    val horizontals = input.map{ case line_format(a,b,c,d) => (a.toInt, b.toInt, c.toInt, d.toInt)}
      .flatMap{ case (x1,y1,x2,y2) => if(x1 == x2 || y1 == y2) {
        val x_values = (Math.min(x1,x2) to Math.max(x1,x2)).toList
        val y_values = (Math.min(y1,y2) to Math.max(y1,y2)).toList
        x_values.zipAll(y_values,x1,y1)
      }else None
      }.groupMapReduce(identity)(_ => 1)(_ + _).filter(_._2 > 1)

    val all = input.map{ case line_format(a,b,c,d) => (a.toInt, b.toInt, c.toInt, d.toInt)}
      .flatMap{ case (x1,y1,x2,y2) =>
        val x_values = if(x1 > x2)(x1 to x2 by -1).toList else (x1 to x2).toList
        val y_values = if(y1 > y2)(y1 to y2 by -1).toList else (y1 to y2).toList
        x_values.zipAll(y_values,x1,y1)
      }.groupMapReduce(identity)(_ => 1)(_ + _).filter(_._2 > 1)

    val output1 = horizontals.size
    val output2 = all.size

    println(output1)
    println(output2)

    val endTime = System.nanoTime()
    val duration = (endTime - startTime) / 1e9d
    println(s"Runtime: $duration seconds")
  }
}

object Day5 {
  def main(args: Array[String]): Unit = {
    val app = new Day5()

    app.run("./src/main/scala/aoc2021/Day5/Example.txt")
    app.run("./src/main/scala/aoc2021/Day5/Input.txt")
  }
}