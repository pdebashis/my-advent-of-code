package aoc2024.Day3

import scala.io.Source
import scala.util.matching.Regex

class Day3 {
  def run(inputFile: String) =  {
    println(s"Executing for ${inputFile.split("/").last}")
    val input = Source.fromFile(inputFile).getLines().toList

    val mul_regex = new Regex("mul\\(\\d{1,3},\\d{1,3}\\)")
    val nums_regex = new Regex("\\d{1,3}")
    val do_regex = new Regex("do\\(\\)")
    val dont_regex = new Regex("don't\\(\\)")

    val output1 = input.map { row =>
      val mul_patterns = mul_regex.findAllIn(row.trim).toList
      mul_patterns.map(x => nums_regex.findAllIn(x).toList.map(_.toInt).reduce(_*_)).sum
    }.sum

    val input2 = input.reduce(_++_)
    val dont_pos = dont_regex.findAllMatchIn(input2).map(m => m.start).toList
    val do_pos = do_regex.findAllMatchIn(input2).map(m => m.start).toList
    val mul_patterns = mul_regex.findAllMatchIn(input2.trim).map(m => (m.matched, m.start)).toList

    val disabled_pos = dont_pos.flatMap { i =>
      val enable = do_pos.find(x => x > i)
      if (enable.isDefined) Some(i, enable.get)
      else Some(i, Int.MaxValue)
    }
    val output2 = mul_patterns
      .filterNot(m => disabled_pos.exists(p => p._1 < m._2 && p._2 > m._2))
      .map(x => nums_regex.findAllIn(x._1).toList.map(_.toInt).reduce(_ * _)).sum

    println(output1)
    println(output2)
  }
}

object Day3 {
  def main(args: Array[String]): Unit = {
    val app = new Day3()
    app.run("./src/main/scala/aoc2024/Day3/Example.txt")
    app.run("./src/main/scala/aoc2024/Day3/Input.txt")
  }
}