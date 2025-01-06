package aoc2021.Day2

class Day2 {
  def run(inputFile: String) :Unit =  {
    val startTime = System.nanoTime()
    println(s"Executing for ${inputFile.split("/").last}")
    val source = scala.io.Source.fromFile(inputFile)
    val input = try{
      source.getLines.toList
    } finally {
      source.close()
    }

    val position = input.map( dir => dir.split(" ")).foldLeft((0,0)){ case ((hor, ver), dir) =>
      dir(0) match {
        case "forward" => (hor + dir(1).toInt, ver)
        case "down" => (hor, ver + dir(1).toInt)
        case "up" => (hor, ver - dir(1).toInt)
        case _ => (hor, ver)
      }
    }


    val positionAim = input.map( dir => dir.split(" ")).foldLeft((0,0,0)){ case ((hor, ver, aim), dir) =>
      dir(0) match {
        case "forward" => (hor + dir(1).toInt, ver + (aim * dir(1).toInt), aim)
        case "down" => (hor, ver, aim + dir(1).toInt)
        case "up" => (hor, ver, aim - dir(1).toInt)
        case _ => (hor, ver, aim)
      }
    }

    val output1 = position._1 * position._2
    val output2 = positionAim._1 * positionAim._2

    println(output1)
    println(output2)

    val endTime = System.nanoTime()
    val duration = (endTime - startTime) / 1e9d
    println(s"Runtime: $duration seconds")
  }
}

object Day2 {
  def main(args: Array[String]): Unit = {
    val app = new Day2()
    
    app.run("./src/main/scala/aoc2021/Day2/Example.txt")
    app.run("./src/main/scala/aoc2021/Day2/Input.txt")
  }
}