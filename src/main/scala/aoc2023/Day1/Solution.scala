package aoc2023.Day1

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

    val output1 = input.map(_.toCharArray.filter(_.isDigit).toList).map( l => if(l.isEmpty) "0" else l.head.toString + l.last.toString).map(_.toInt).sum
    val output2 = input.map(_.replaceAll("one","one1one"))
      .map(_.replaceAll("two","two2two"))
      .map(_.replaceAll("three","three3"))
      .map(_.replaceAll("four","4"))
      .map(_.replaceAll("five","5five"))
      .map(_.replaceAll("six","6"))
      .map(_.replaceAll("seven","7"))
      .map(_.replaceAll("eight","eight8eight"))
      .map(_.replaceAll("nine","9nine"))
      .map(_.toCharArray.filter(_.isDigit).toList).map( l => if(l.isEmpty) "0" else l.head.toString + l.last.toString).map(_.toInt).sum

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

    app.run("./src/main/scala/aoc2023/Day1/Example.txt")
    app.run("./src/main/scala/aoc2023/Day1/Input.txt")
  }
}