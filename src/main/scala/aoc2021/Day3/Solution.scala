package aoc2021.Day3

import scala.annotation.tailrec
import scala.io.Source

class Day3 {

  def toDecimal(str: String) = {
    str.reverse.zipWithIndex.map{ case (c,i) => if(c=='1') Math.pow(2,i) else 0 }.sum
  }

  def run(inputFile: String) :Unit =  {
    val startTime = System.nanoTime()
    println(s"Executing for ${inputFile.split("/").last}")
    val source = scala.io.Source.fromFile(inputFile)
    val input = try{
      source.getLines.toList
    } finally {
      source.close()
    }

    val selectCommon = input
      .map( x => x.toCharArray)
      .transpose
      .map( cArray => cArray.count(_ == '1'))
      .map( count => if(count>input.size/2) '1' else '0')
      .mkString

    val selectUnCommon = input
      .map( x => x.toCharArray)
      .transpose
      .map( cArray => cArray.count(_ == '1'))
      .map( count => if(count>input.size/2) '0' else '1')
      .mkString

    val gammaRate = toDecimal(selectCommon)
    val epsilonRate = toDecimal(selectUnCommon)

    @tailrec
    def mostCommon( bins :List[String], pos: Int) : String = {
      if(bins.size == 1) bins.head
      else{
        val bits = bins.map( x=> x.charAt(pos))
        val count0 = bits.count(_ == '0')
        val count1 = bits.count(_ == '1')
        val common = count1.compare(count0) match {
          case 1 => '1'
          case 0 => '1'
          case -1 => '0'
        }
        val newBins = bins.filter(x => x.charAt(pos) == common)
        mostCommon(newBins, pos+1)
      }
    }

    @tailrec
    def leastCommon( bins :List[String], pos: Int) : String = {
      if(bins.size == 1) bins.head
      else{
        val bits = bins.map( x=> x.charAt(pos))
        val count0 = bits.count(_ == '0')
        val count1 = bits.count(_ == '1')
        val uncommon = count1.compare(count0) match {
          case 1 => '0'
          case 0 => '0'
          case -1 => '1'
        }
        val newBins = bins.filter(x => x.charAt(pos) == uncommon)
        leastCommon(newBins, pos+1)
      }
    }

    val oxyRate = toDecimal(mostCommon(input, 0))
    val co2Rate = toDecimal(leastCommon(input, 0))

    val output1 = (gammaRate * epsilonRate).toLong
    val output2 = (oxyRate * co2Rate).toLong

    println(output1)
    println(output2)

    val endTime = System.nanoTime()
    val duration = (endTime - startTime) / 1e9d
    println(s"Runtime: $duration seconds")
  }
}

object Day3 {
  def main(args: Array[String]): Unit = {
    val app = new Day3()

    app.run("./src/main/scala/aoc2021/Day3/Example.txt")
    app.run("./src/main/scala/aoc2021/Day3/Input.txt")
  }
}