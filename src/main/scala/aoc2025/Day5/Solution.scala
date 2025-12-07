package aoc2025.Day5

import scala.io.Source

class Day5 {
  def recursivelyShortenRanges(ranges: List[(Long, Long)]): List[(Long, Long)] = {
    def helper(ranges: List[(Long, Long)], acc: List[(Long, Long)]): List[(Long, Long)] = {
      ranges match {
        case Nil => acc.reverse
        case head :: tail =>
          acc match {
            case Nil => helper(tail, head :: acc)
            case last :: rest =>
              if (head._1 <= last._2 + 1) {
                val merged = (last._1, Math.max(last._2, head._2))
                helper(tail, merged :: rest)
              } else {
                helper(tail, head :: acc)
              }
          }
      }
    }
    helper(ranges, Nil)
  }

  def run(inputFile: String) :Unit =  {
    val startTime = System.nanoTime()
    println(s"Executing for ${inputFile.split("/").last}")
    val source = scala.io.Source.fromFile(inputFile)
    val input = try{
      source.getLines.toList
    } finally {
      source.close()
    }

    val ingredients = input.filter(_.nonEmpty).filterNot(_.contains("-")).map(_.toLong)
    
    val fresh_ranges = input.filter(_.nonEmpty).filter(_.contains("-")).map { line =>
      val parts = line.split("-").take(2).map(_.toLong)
      (parts(0), parts(1))
    }.sortBy(_._1)

    val checks = ingredients.map { x =>
      fresh_ranges.exists{
        case (start, end) => x >= start && x <= end
      }}

    val fresh_ranges_reduced = recursivelyShortenRanges(fresh_ranges)
    
    val output1 = checks.count(_ == true)
    val output2 = fresh_ranges_reduced.map{
       case (start, end) => end - start + 1}.sum

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

    app.run("./src/main/scala/aoc2025/Day5/Example.txt")
    app.run("./src/main/scala/aoc2025/Day5/Input.txt")
  }
}