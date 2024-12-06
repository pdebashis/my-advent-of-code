import scala.io.Source

class ??? {
  def run(inputFile: String) =  {
    println(s"Executing for ${inputFile.split("/").last}")
    val input = Source.fromFile(inputFile).getLines().toList

    val output1 = 0
    val output2 = 0

    println(output1)
    println(output2)
  }
}

object ??? {
  def main(args: Array[String]): Unit = {
    val app = new ???()
    app.run("./src/main/scala/aoc%YEAR%/Day%DAY%/Example.txt")
//    app.run("./src/main/scala/aoc%YEAR%/Day%DAY%/Input.txt")
  }
}