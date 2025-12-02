package aoc2025.Day2

import scala.io.Source

class Day2 {

  def numRepeatative_1(numStr: String): Long = {
    val len = numStr.length
    if(len%2 != 0) return 0
    val halfLen = len/2
    if (numStr.substring(0, halfLen) == numStr.substring(halfLen, len))
      numStr.toLong
    else
      0
  }

  def numRepeatative_2(numStr: String): Long = {
    val len = numStr.length
    val halfLen = len/2
    len match {
      case 2 => if(numStr.toInt % 11 == 0) numStr.toLong else 0
      case 3 => if(numStr.chars().distinct().count() == 1) numStr.toLong else 0
      case 4=> if (numStr.substring(0, halfLen) == numStr.substring(halfLen, len)) numStr.toLong else 0
      case 5 => if(numStr.chars().distinct().count() == 1) numStr.toLong else 0
      case 6 => if (numStr.substring(0, halfLen) == numStr.substring(halfLen, len)) numStr.toLong else if (numStr.substring(0, 2) == numStr.substring(2, 4) && numStr.substring(0, 2) == numStr.substring(4, 6)) numStr.toLong else 0
      case 7 => if(numStr.chars().distinct().count() == 1) numStr.toLong else 0
      case 8 => if (numStr.substring(0, halfLen) == numStr.substring(halfLen, len)) numStr.toLong else 0
      case 9 => if(numStr.chars().distinct().count() == 1) numStr.toLong else if(numStr.substring(0, 3) == numStr.substring(3, 6) && numStr.substring(0, 3) == numStr.substring(6, 9)) numStr.toLong else 0
      case 10 => if (numStr.substring(0, halfLen) == numStr.substring(halfLen, len)) numStr.toLong 
        else if (numStr.substring(0, 2) == numStr.substring(2, 4) && numStr.substring(0, 2) == numStr.substring(4, 6) && numStr.substring(0, 2) == numStr.substring(6, 8) && numStr.substring(0, 2) == numStr.substring(8, 10)) numStr.toLong else 0
      case _ => 0
    }
  }

  def run(inputFile: String) =  {
    println(s"Executing for ${inputFile.split("/").last}")
    val input = Source.fromFile(inputFile).getLines().toList

    val inputSplit = input.flatMap(l => l.split(","))

    println(inputSplit)

    val output1 = inputSplit.map( range => {
      val parts = range.split("-")
      val start = parts(0).toLong
      val end = parts(1).toLong
      // println(s"Processing range: $start to $end")
      (start to end).foldLeft(0.toLong)((acc, num) => {
        val numStr = num.toString()
        acc + numRepeatative_1(numStr).toLong
      })
    }).sum
    val output2 = inputSplit.map( range => {
      val parts = range.split("-")
      val start = parts(0).toLong
      val end = parts(1).toLong
      // println(s"Processing range: $start to $end")
      (start to end).foldLeft(0.toLong)((acc, num) => {
        val numStr = num.toString()
        // if(numRepeatative_2(numStr) != 0) println(numRepeatative_2(numStr))
        acc + numRepeatative_2(numStr).toLong
      })
    }).sum

    println(output1)
    println(output2)
  }
}

object Day2 {
  def main(args: Array[String]): Unit = {
    val app = new Day2()
    // println(app.numRepeatative_2("1212")) // true
    // println(app.numRepeatative_2("123123")) // true  
    // println(app.numRepeatative_2("121212")) // false  
    app.run("./src/main/scala/aoc2025/Day2/Example.txt")
    app.run("./src/main/scala/aoc2025/Day2/Input.txt")
  }
}