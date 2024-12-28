package aoc2024.Day10

import scala.io.Source

class Day10 {

  def run(inputFile: String) :Unit =  {
    val startTime = System.nanoTime()
    println(s"Executing for ${inputFile.split("/").last}")
    val source = scala.io.Source.fromFile(inputFile)
    val input = try{
      source.getLines.toList
    } finally {
      source.close()
    }

    val grid = input.toArray

    def find_paths(x: Int,y:Int) : Set[(Int, Int)] = {
      val curr = grid(x)(y).asDigit
      val left = if(y>0) grid(x)(y-1).asDigit else 0
      val right = if(y<grid.last.length-1) grid(x)(y+1).asDigit else 0
      val top = if(x>0) grid(x-1)(y).asDigit else 0
      val bot = if(x<grid.length-1) grid(x+1)(y).asDigit else 0

      if(curr == 9){
        Set((x,y))
      }
      else {

        val left_paths = if (left - curr == 1) find_paths(x, y - 1) else Set.empty
        val right_paths = if (right - curr == 1) find_paths(x, y + 1) else Set.empty
        val top_paths = if (top - curr == 1) find_paths(x - 1, y) else Set.empty
        val bot_paths = if (bot - curr == 1) find_paths(x + 1, y) else Set.empty

        left_paths ++ right_paths ++ top_paths ++ bot_paths
      }
    }

    def find_all_paths(x: Int,y:Int) : Int = {
      val curr = grid(x)(y).asDigit
      val left = if(y>0) grid(x)(y-1).asDigit else 0
      val right = if(y<grid.last.length-1) grid(x)(y+1).asDigit else 0
      val top = if(x>0) grid(x-1)(y).asDigit else 0
      val bot = if(x<grid.length-1) grid(x+1)(y).asDigit else 0

      if(curr == 9){
        1
      }
      else {

        val left_paths = if (left - curr == 1) find_all_paths(x, y - 1) else 0
        val right_paths = if (right - curr == 1) find_all_paths(x, y + 1) else 0
        val top_paths = if (top - curr == 1) find_all_paths(x - 1, y) else 0
        val bot_paths = if (bot - curr == 1) find_all_paths(x + 1, y) else 0

        left_paths + right_paths + top_paths + bot_paths
      }
    }

    val trailheads_score = (for {
      x <- grid.indices
      y <- grid(x).indices
      if grid(x)(y) == '0'
    } yield {
      val result = find_paths(x, y)
      result.size
    }).sum

    val trailheads_rating = (for {
      x <- grid.indices
      y <- grid(x).indices
      if grid(x)(y) == '0'
    } yield {
      find_all_paths(x, y)
    }).sum

    val output1 = trailheads_score
    val output2 = trailheads_rating

    println(output1)
    println(output2)

    val endTime = System.nanoTime()
    val duration = (endTime - startTime) / 1e9d
    println(s"Runtime: $duration seconds")
  }
}

object Day10 {
  def main(args: Array[String]): Unit = {
    val app = new Day10()

    app.run("./src/main/scala/aoc2024/Day10/Example.txt")
    app.run("./src/main/scala/aoc2024/Day10/Input.txt")
  }
}