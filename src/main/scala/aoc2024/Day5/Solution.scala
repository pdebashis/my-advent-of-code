package aoc2024.Day5

import scala.io.Source

class Day5 {
  def run(inputFile: String) =  {
    println(s"Executing for ${inputFile.split("/").last}")
    val input = Source.fromFile(inputFile).getLines().toList

    val ordering = input.filter(_.contains("|")).map{ str =>
      val Array(x, y) = str.split("\\|").map(_.toInt)
      (x,y)
    }
    val pages = input.filter(_.contains(",")).map( x=> x.split(",").map(_.toInt).toSeq)

    val after_pages :Map[Int,List[Int]] = ordering.groupBy( _._1).map( x => (x._1, x._2.map(_._2)))
    val before_pages :Map[Int,List[Int]] = ordering.map{case (a,b)=>(b,a)}.groupBy(_._1).map( x => (x._1, x._2.map(_._2)))

    println(after_pages)
    println(before_pages)

    def midIfValid(page: Seq[Int]) :Int = {
      val valid = page.indices.forall{ i =>
        val left = page.take(i)
        val right = page.drop(i+1)
        val curr = page(i)

        left.forall(p => !after_pages.getOrElse(curr,List()).exists(_ == p))
        right.forall(p => !before_pages.getOrElse(curr,List()).exists(_ == p))
      }
      if(valid) page(page.size/2)
      else 0
    }

    def midAll(page: Seq[Int]) :Int = {
      ordering.filter( x => page.contains(x._1) && page.contains(x._2))
        .groupBy(_._1).mapValues(_.size)
        .filter( _._2 == page.size/2 )
        .head
        ._1
    }

    val output1 = pages.map(x => midIfValid(x)).sum
    val output2 = pages.map(x => midAll(x)).sum - output1

    println(output1)
    println(output2)
  }
}

object Day5 {
  def main(args: Array[String]): Unit = {
    val app = new Day5()
    app.run("./src/main/scala/aoc2024/Day5/Example.txt")
    app.run("./src/main/scala/aoc2024/Day5/Input.txt")
  }
}