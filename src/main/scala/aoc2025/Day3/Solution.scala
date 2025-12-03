package aoc2025.Day3

import scala.io.Source

class Day3 {

  // def max_joltage(line: String): Int = {
  //   val batteries = line.map(_.toString.toInt).toArray.toList
  //   val max_first_digit = batteries.take(batteries.length - 1).max
  //   val max_first_index = batteries.indexOf(max_first_digit)
  //   val max_second_digit = batteries.drop(max_first_index+1).max

  //   // println(s"Max joltage: ${max_first_digit} * 10 + ${max_second_digit} = ${max_first_digit*10 + max_second_digit}")
  //   max_first_digit*10 + max_second_digit
  // }

  def max_joltage_n(line: String, n: Int): String = {
    //Functions-------------------------------------------------
    if (n == 1) {
      return line.max.toString
    }
    // println(s"Finding max in Line: ${line} for size n: ${n}")
    val batteries = line.map(_.toString.toInt).toArray.toList
    val max_first_digit = batteries.take(batteries.length - (n-1)).max
    // println(s"Found max digit: ${max_first_digit} in ${batteries.take(batteries.length - (n-1))}")
    val rest_batteries = max_joltage_n(line.drop(batteries.indexOf(max_first_digit)+1), n-1)

    max_first_digit.toString + max_joltage_n(line.drop(batteries.indexOf(max_first_digit)+1), n-1)
  }

  def run(inputFile: String) =  {
    // Read Input-------------------------------------------------
    println(s"Executing for ${inputFile.split("/").last}")
    val input = Source.fromFile(inputFile).getLines().toList //["987654321111111","987654321111111"]

    //Logic-------------------------------------------------------

    // input.map(max_joltage_n(_, 2))

    //Output-------------------------------------------------------
    val output1 = input.map(max_joltage_n(_, 2)).map(_.toLong).sum
    val output2 = input.map(max_joltage_n(_, 12)).map(_.toLong).sum

    println(output1)
    println(output2)
  }
}

object Day2 {
  def main(args: Array[String]): Unit = {
    val app = new Day3()

    println(app.max_joltage_n("818181911112111", 12))

    // app.run("./src/main/scala/aoc2025/Day3/Example.txt")
    app.run("./src/main/scala/aoc2025/Day3/Input.txt")
  }
}