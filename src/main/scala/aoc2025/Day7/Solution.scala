package aoc2025.Day7

import scala.io.Source

class Day7 {

  def asc_desc_gradual(row : List[Int]) = {
    val difference_list = row.foldLeft(List[(Int, Int)]()){ (acc, curr) =>
      if(acc.isEmpty) acc :+ (curr,0)
      else acc :+ (curr, curr-acc.last._1)
    }.tail.map(_._2)

    val asc_gradual = difference_list.forall( x => x > 0 && x <= 3)
    val desc_gradual = difference_list.forall( x => x < 0 && x >= -3)
    asc_gradual || desc_gradual
  }

  def asc_gradual_conservative(row : List[Int]) = {
    val asc_desc : Boolean = asc_desc_gradual(row)
    if(asc_desc){
      true
    }else {
      row.indices.exists { i =>
        val without_i = row.take(i) ++ row.drop(i + 1)
        asc_desc_gradual(without_i)
      }
    }
  }

  def run(inputFile: String) =  {
    println(s"Executing for ${inputFile.split("/").last}")
    val input = Source.fromFile(inputFile).getLines().toList

    val output1 = input.map( x=> x.split("\\s+").map(_.toInt).toList).filter( row => asc_desc_gradual(row)).size
    val output2 = input.map( x=> x.split("\\s+").map(_.toInt).toList).filter( row => asc_gradual_conservative(row)).size

    println(output1)
    println(output2)
  }
}

object Day2 {
  def main(args: Array[String]): Unit = {
    val app = new Day7()
    app.run("./src/main/scala/aoc2024/Day2/Example.txt")
    app.run("./src/main/scala/aoc2024/Day2/Input.txt")
  }
}