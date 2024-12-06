package aoc2024.Day4

import scala.io.Source
import scala.util.matching.Regex

class Day4 {

  def run(inputFile: String) =  {
    println(s"Executing for ${inputFile.split("/").last}")
    val input = Source.fromFile(inputFile).getLines().toList

    val grid = input.toArray
    val word = "XMAS"
    val directions = Seq(
      (-1,-1),  (-1,0),   (-1,1),
      (0 ,-1),            (0 ,1),
      (1 ,-1),  (1 ,0),   (1 ,1)
    )

    val patterns = List(
      "M.M..S.S",
      "M.S..M.S",
      "S.M..S.M",
      "S.S..M.M")

    def isValidPartOne(x: Int, y: Int, dx: Int, dy: Int, word: String): Boolean = {
      grid(x)(y) == word(0) &&
      (1 until word.length).forall(i =>
        x + i * dx >= 0 && x + i * dx < grid.length &&
          y + i * dy >= 0 && y + i * dy < grid(x).length &&
          grid(x + i * dx)(y + i * dy) == word(i)
      )
    }

    def isValidPartTwo(x: Int, y: Int): Boolean = {
      val surrounds = directions.map( c => (x+c._1,y+c._2)).collect{
        case (x,y) if x>=0 && x<grid.length && y>=0 && y < grid.length => grid(x)(y)
      }.mkString

      (grid(x)(y) == 'A') &&
        (patterns.exists( x => x.r.matches(surrounds)))
    }

    val output1 = (for {
      x <- grid.indices
      y <- grid(x).indices
      (dx, dy) <- directions
      if isValidPartOne(x, y, dx, dy, word)
    } yield {1}).size

    val output2 = (for {
      x <- grid.indices
      y <- grid(x).indices
      if isValidPartTwo(x, y)
    } yield 1).size

    println(output1)
    println(output2)
  }
}

object Day4 {
  def main(args: Array[String]): Unit = {
    val app = new Day4()
    app.run("./src/main/scala/aoc2024/Day4/Example.txt")
    app.run("./src/main/scala/aoc2024/Day4/Input.txt")
  }
}