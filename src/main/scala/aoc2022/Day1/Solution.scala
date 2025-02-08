package aoc2022.Day1

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

    val calories = (input :+ "").foldLeft((List[List[Int]](), List[Int]()) )( (acc, curr) =>
      if(curr.isBlank) {
        val total_list = acc._1 :+ acc._2
        val acc_list = List[Int]()
        (total_list,acc_list)
      }
      else {
        val acc_list = acc._2 :+ curr.toInt
        (acc._1, acc_list)
      }
    )._1

    val output1 = calories.map(_.sum).max
    val output2 = calories.map(_.sum).sortBy(-_).take(3).sum

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

    app.run("./src/main/scala/aoc2022/Day1/Example.txt")
    app.run("./src/main/scala/aoc2022/Day1/Input.txt")
  }
}