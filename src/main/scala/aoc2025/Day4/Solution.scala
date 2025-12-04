package aoc2025.Day4

import scala.io.Source

class Day4 {
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
    val directions = Seq(
      (-1,-1),  (-1,0),   (-1,1),
      (0 ,-1),            (0 ,1),
      (1 ,-1),  (1 ,0),   (1 ,1)
    )
    val width = grid(0).length
    val height = grid.length
    def validX(x: Int): Boolean = x >= 0 && x < height
    def validY(y: Int): Boolean = y >= 0 && y < width
    def count_adjacent_rolls(grid: Array[String] ,x: Int, y: Int): Int = {
      directions.map { case (dx, dy) =>
        val newX = x + dx
        val newY = y + dy
        if (validX(newX) && validY(newY) && grid(newX)(newY) == '@') 1 else 0
      }.sum
    }
    def toGrid(indexedSeq: IndexedSeq[Char]) = indexedSeq.grouped(width).map(_.mkString).toArray
    def toSeq(grid: Array[String]) = grid.toIndexedSeq.flatMap(_.toCharArray())

    val removed_rolls = (
      for(x <- grid.indices; 
          y <- grid(x).indices) 
      yield {
        val curr = grid(x)(y)
        if(curr == '@')
          if(count_adjacent_rolls(grid, x, y) < 4) 'x' else curr
        else
          curr
      })

    val output1 = removed_rolls.count(_ == 'x')
    
    def keep_removing_rolls(grid: Array[String], newGrid: Array[String]) :Array[String] = {
      // println(s"Current Grid: ${toSeq(grid).count(_ == 'x')} removed rolls")
      // println(s"Removed: ${toSeq(newGrid).count(_ == 'x')} rolls")
      if(toSeq(grid).count(_ == 'x') == toSeq(newGrid).count(_ == 'x')) {
        newGrid
      } else {
        val next = (for(x <- newGrid.indices; y <- newGrid(x).indices) yield {
          val curr = newGrid(x)(y)
          if(curr == '@')
            if(count_adjacent_rolls(newGrid, x, y) < 4) 'x' else curr
          else
            curr
        })
        keep_removing_rolls(newGrid, toGrid(next))
      }
    }
    val output2 = toSeq(keep_removing_rolls(grid, toGrid(removed_rolls))).count(_ == 'x')

    println(output1)
    println(output2)

    val endTime = System.nanoTime()
    val duration = (endTime - startTime) / 1e9d
    println(s"Runtime: $duration seconds")
  }
}

object Day4 {
  def main(args: Array[String]): Unit = {
    val app = new Day4()

    app.run("./src/main/scala/aoc2025/Day4/Example.txt")
    app.run("./src/main/scala/aoc2025/Day4/Input.txt")
  }
}