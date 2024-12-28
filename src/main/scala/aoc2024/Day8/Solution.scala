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

    val frequency_map: Map[Char, List[(Int, Int)]] = grid.zipWithIndex.flatMap{
      case (str, x) => str.indices.map( y => (str(y),(x,y)))
    }.groupBy( _._1).mapValues( x => x.map(_._2).toList).toMap

    val regular_antinodes = (for{
      x1 <- grid.indices
      y1 <- grid.last.indices
      if grid(x1)(y1) != '.'
    } yield {
      val antenna = grid(x1)(y1)
      val other_antennas = frequency_map(antenna).filter( _ != (x1,y1) ).filter( _._2 >= y1)
      other_antennas.flatMap{ case (x2,y2) =>
        val x_dist = x2 - x1
        val y_dist = y2 - y1
        List((x1 - x_dist,y1 - y_dist),(x2 + x_dist,y2 + y_dist))
      }.filter( c => c._1>=0 && c._1<grid.length && c._2 >=0 && c._2 < grid.last.length)
    }).toList.flatten

    def cal_left_antinodes(c: (Int,Int), x_dist:Int,y_dist:Int) :List[(Int,Int)] = {
      if(c._1 < 0 || c._2 < 0) List()
      else if(c._1 >= grid.length || c._2 >= grid.last.length) List()
      else List(c) ++ cal_left_antinodes((c._1 - x_dist, c._2 - y_dist), x_dist, y_dist)
    }

    def cal_right_antinodes(c: (Int,Int), x_dist:Int,y_dist:Int) :List[(Int,Int)] = {
      if(c._1 < 0 || c._2 < 0) List()
      else if(c._1 >= grid.length || c._2 >= grid.last.length) List()
      else List(c) ++ cal_right_antinodes((c._1 + x_dist, c._2 + y_dist), x_dist, y_dist)
    }

    val resonant_antinodes = (for{
      x1 <- grid.indices
      y1 <- grid.last.indices
      if grid(x1)(y1) != '.'
    } yield {
      val antenna = grid(x1)(y1)
      val other_antennas = frequency_map(antenna).filter( _ != (x1,y1) ).filter( _._2 >= y1)
      other_antennas.flatMap { case (x2, y2) =>
        val x_dist = x2 - x1
        val y_dist = y2 - y1
        val left_antinodes = cal_left_antinodes((x1, y1), x_dist, y_dist)
        val right_antinodes = cal_right_antinodes((x2, y2), x_dist, y_dist)
        left_antinodes ++ right_antinodes
      }
    }).toList.flatten

    val output1 = regular_antinodes.distinct.size
    val output2 = resonant_antinodes.distinct.size

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