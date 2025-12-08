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
      line.trim.split("\\s+").filter(_.nonEmpty).map(_.toLong)
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
    
    val num_lines_new = input.filterNot(_.contains("*")).transpose
    val nums_list_new = num_lines_new.map(_.mkString).map{ x=> 
      if(x.replaceAll("\\s+","").isEmpty()) 
        "" 
      else 
        x
    }

    val folded = nums_list_new.foldLeft(List[List[String]]()){ (acc, curr) => 
      curr match {
        case "" => Nil :: acc
        case _ => acc match {
          case Nil => List(List(curr))
          case head :: tail => (curr :: head) :: tail
        }
      }
    }.reverse
    
    // println(ops_line.foreach(println))
    // println(ops_line.indices)
    // println(ops_line.length)
    // println(num_lines_2)

    val math_processor_2 = for(i <- ops_line.indices) yield ({ 
      val num_list = folded(i).map(_.trim.toLong)
      ops_line(i) match {
        case "*" => num_list.reduce(_ * _)
        case "+" => num_list.reduce(_ + _)
        case _ => 0
      }
    })

    val output1 = math_processor.sum
    val output2 = math_processor_2.sum

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

  // app.run("./src/main/scala/aoc2025/Day6/Example.txt")
  app.run("./src/main/scala/aoc2025/Day6/Input.txt")
  }
}