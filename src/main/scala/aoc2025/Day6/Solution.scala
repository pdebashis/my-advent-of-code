package aoc2025.Day6

import scala.io.Source

class Day6 {
  def run(inputFile: String) :Unit =  {
    val startTime = System.nanoTime()
    println(s"Executing for ${inputFile.split("/").last}")
    val source = scala.io.Source.fromFile(inputFile)
    val input = try{
      source.getLines.toList
    } finally {
      source.close()
    }

    val num_lines = input.filterNot(_.contains("*")).map{ line => 
      line.trim.replaceAll("\\s+", " ").split(" ").map(_.toLong)
    }
    val ops_line = input.filter(_.contains("*")).head.trim.replaceAll("\\s+", " ").split(" ")
    val math_processor = for(i <- ops_line.indices) yield ({ 
      val num_list = num_lines.map(_(i))
      ops_line(i) match {
        case "*" => num_list.reduce(_ * _)
        case "+" => num_list.reduce(_ + _)
        case _ => 0
      }
    })
    
    //println(math_processor)

    val num_lines_new = input.filterNot(_.contains("*")).transpose.map(_.filterNot(_ == ' ')).filterNot( _ == List())
    val nums_list_new = num_lines_new.map(_.mkString)
    
    println(nums_list_new)

    val output1 = math_processor.sum
    val output2 = 0 //math_processor_c.sum

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

  app.run("./src/main/scala/aoc2025/Day6/Example.txt")
  // app.run("./src/main/scala/aoc2025/Day6/Input.txt")
  }
}