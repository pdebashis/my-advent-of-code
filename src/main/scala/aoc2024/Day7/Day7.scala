package aoc2024.Day7

import scala.annotation.tailrec
import scala.io.Source
import scala.util.Using

class Day7 {
  def run(inputFile: String):Unit =  {
    val startTime = System.nanoTime()
    println(s"Executing for ${inputFile.split("/").last}")
    val source = scala.io.Source.fromFile(inputFile)
    val input = try{
      source.getLines.toList
    } finally {
      source.close()
    }

    def evaluate(nums : List[Long], t: Long, partTwo: Boolean) : Boolean = {
      val new_t_mul = if(t % nums.head == 0) t / nums.head else -1
      val new_t_plus = t - nums.head

      if(nums.size == 1) {
        nums.head == t
      }else{
        val checkAddition = if (new_t_plus > 0) evaluate(nums.tail, new_t_plus, partTwo) else false
        val checkMultiplication = if (new_t_mul > 0) evaluate(nums.tail, new_t_mul, partTwo) else false
        val checkConcat = if(partTwo && t.toString.endsWith(nums.head.toString) && t.toString.stripSuffix(nums.head.toString).sizeIs > 0) evaluate(nums.tail, t.toString.stripSuffix(nums.head.toString).toLong, partTwo)
        else false
        checkAddition || checkMultiplication || checkConcat
      }
    }

    val output1 = input.map { line =>
      val target = line.split(":")(0).toLong
      val numbers = line.split(":")(1).trim.split("\\s+").toList.map(_.toLong).reverse
      if(evaluate(numbers, target, false)) target
      else 0
    }.sum
    val output2 = input.map { line =>
      val target = line.split(":")(0).toLong
      val numbers = line.split(":")(1).trim.split("\\s+").toList.map(_.toLong).reverse
      if(evaluate(numbers, target, true)) target
      else 0
    }.sum

    println(output1)
    println(output2)

    val endTime = System.nanoTime()
    val duration = (endTime - startTime) / 1e9d
    println(s"Runtime: $duration seconds")

  }
}

object Day7 {
  def main(args: Array[String]): Unit = {

    val app = new Day7()
    app.run("./src/main/scala/aoc2024/Day7/Example.txt")
    app.run("./src/main/scala/aoc2024/Day7/Input.txt")
  }
}