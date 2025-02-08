package aoc2021.Day6

import scala.collection.mutable

class Day6 {
  def run(inputFile: String) :Unit =  {
    val startTime = System.nanoTime()
    println(s"Executing for ${inputFile.split("/").last}")
    val source = scala.io.Source.fromFile(inputFile)
    val input = try{
      source.getLines.toList.head
    } finally {
      source.close()
    }

    val map_of_lanterns = mutable.Map[(Int,Int),Long]()

    def creation(fish: Int, days:Int) :Long = {
      if(map_of_lanterns.contains((fish,days))) map_of_lanterns.apply((fish,days))
      else if(fish > days) {

        map_of_lanterns((6, fish - days - 1)) = creation(6, fish - days - 1)
        map_of_lanterns((8, fish - days - 1)) = creation(8, fish - days - 1)
        map_of_lanterns((6, fish - days - 1)) + map_of_lanterns((8, fish - days - 1))
      }else 1
    }

    val output1 = input.split(",").map(_.toInt).map(creation(_, 80)).sum
    val output2 = input.split(",").map(_.toInt).map(creation(_, 256)).sum

    println(output1)
    println(output2)

    val endTime = System.nanoTime()
    val duration = (endTime - startTime) / 1e9d
    println(s"Runtime: $duration seconds")
  }
}

object Day6 {
  def main(args: Array[String]): Unit = {
    val app = new Day6()

    app.run("./src/main/scala/aoc2021/Day6/Example.txt")
    app.run("./src/main/scala/aoc2021/Day6/Input.txt")
  }
}