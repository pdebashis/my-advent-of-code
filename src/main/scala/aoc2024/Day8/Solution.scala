package aoc2024.Day8

import scala.io.Source

class Day8 {
  def run(inputFile: String):Unit =  {
    val startTime = System.nanoTime()
    println(s"Executing for ${inputFile.split("/").last}")
    val source = scala.io.Source.fromFile(inputFile)
    val input = try{
      source.getLines.toList
    } finally {
      source.close()
    }

    val grid = input.toArray

    val antenna_map: Map[Char, List[(Int, Int)]] = grid.zipWithIndex.flatMap{
      case (str, x) => str.indices.map( y => (str(y),(x,y)))
    }.groupBy( _._1).mapValues( x => x.map(_._2).toList).toMap

    val output1 = (for{
      x1 <- grid.indices
      y1 <- grid.last.indices
      if grid(x1)(y1) != '.'
    } yield {
      val antenna = grid(x1)(y1)
      val x_max = grid.length
      val y_max = grid.last.length
      val other_antennas = antenna_map(antenna).filter( _ != (x1,y1) ).filter( _._2 >= y1)
      other_antennas.flatMap{ case (x2,y2) =>
        val x_dist = x2 - x1
        val y_dist = y2 - y1
        val new_left = (x1 - x_dist,y1 - y_dist)
        val new_right = (x2 + x_dist,y2 + y_dist)
        List(new_left, new_right)
      }.filter( c => c._1>=0 && c._1<x_max && c._2 >=0 && c._2 < y_max)
    }).toList.flatten.distinct.size
    val output2 = 0

    println(output1)
    println(output2)

    val endTime = System.nanoTime()
    val duration = (endTime - startTime) / 1e9d
    println(s"Runtime: $duration seconds")
  }
}

object Day8 {
  def main(args: Array[String]): Unit = {
    val app = new Day8()

    app.run("./src/main/scala/aoc2024/Day8/Example.txt")
    app.run("./src/main/scala/aoc2024/Day8/Input.txt")
  }
}