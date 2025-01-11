package aoc2021.Day4

import scala.io.Source

class Board(rows_str :List[String], nums: Array[Int]) {

  override def toString :String = {
    rows.mkString
  }

  val rows = rows_str.map(_.strip.split("\\s+")).map(row => row.map(_.toInt))
  val ordering = rows.map(_.map( elem => (elem, nums.indexOf(elem))))
  val bingo_horizontal = ordering.map(row => row.maxBy{ case(_, order) => order}).minBy{ case(_, order) => order}
  val bingo_vertical = ordering.transpose.map(row => row.maxBy{ case(_, order) => order}).minBy{ case(_, order) => order}
  val first_bingo = if(bingo_horizontal._2 < bingo_vertical._2) bingo_horizontal else bingo_vertical
  val sum_unmarked = ordering.map(row => row.filter{ case(elem, order) => order > first_bingo._2}.map(_._1).sum).sum
  val score = first_bingo._1 * sum_unmarked

}

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

    val numbers = input.head.strip.split(",").map(_.toInt)
    val boards = input.drop(1).filterNot(_.isBlank).grouped(5).toList.map(new Board(_,numbers))
    val winning_board = boards.minBy(b => b.first_bingo._2)
    val last_board = boards.maxBy(b => b.first_bingo._2)

    val output1 = winning_board.score
    val output2 = last_board.score

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

    app.run("./src/main/scala/aoc2021/Day4/Example.txt")
    app.run("./src/main/scala/aoc2021/Day4/Input.txt")
  }
}