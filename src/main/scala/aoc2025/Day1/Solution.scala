package aoc2025.Day1

import scala.io.Source

class Day1 {
  def new_knob_position(current_pos: Int, rotation: Int): Int = {
      val new_pos = (current_pos + rotation) % 100
      if(new_pos < 0) {
        100 + new_pos
      } else {
        new_pos
      }
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

    val rotations = input.map( x=>
      if(x.startsWith("L"))
        - x.substring(1).toInt
      else
        x.substring(1).toInt
    )
    var knob_pos = 50
    val hit_0 = 0

    def rotate_recurse(knob_pos: Int, rotations: List[Int], hit_0: Int): Int = {
      if(rotations.isEmpty) {
        hit_0
      } else {
        val rotation = rotations.head
        val new_knob_pos = new_knob_position(knob_pos, rotation)
        val new_hit_0 = if(new_knob_pos == 0) hit_0 + 1 else hit_0
        rotate_recurse(new_knob_pos, rotations.tail, new_hit_0)
      }
    }

    def rotate_recurse_2(knob_pos: Int, rotations: List[Int], hit_0: Int): Int = {
      if(rotations.isEmpty) {
        hit_0
      } else {
        val rotation = rotations.head
        val new_knob_pos = new_knob_position(knob_pos, rotation)
        val cross_0 = (knob_pos != 0 && knob_pos + rotation%100 > 100) || (knob_pos != 0 && knob_pos + rotation%100 < 0)
        val full_rotation = Math.abs(rotation/100)
        val at_hit_0 = if(new_knob_pos == 0) hit_0 + 1 else hit_0
        val new_hit_0 = if(cross_0) at_hit_0 + full_rotation + 1 else at_hit_0 + full_rotation
        // print(s"The dial is rotated ${rotation} to point at ${new_knob_pos};")
        // if(full_rotation > 0) print(s"it points at 0 ${full_rotation} times due to full rotation")
        // if(cross_0) println(s"it points at 0 ${full_rotation + 1} times") else println("")
        rotate_recurse_2(new_knob_pos, rotations.tail, new_hit_0)
      }
    }

    val output1 = rotate_recurse(knob_pos, rotations, hit_0)
    val output2 = rotate_recurse_2(knob_pos, rotations, hit_0)

    println(output1)
    println(output2)

    val endTime = System.nanoTime()
    val duration = (endTime - startTime) / 1e9d
    println(s"Runtime: $duration seconds")
  }
}

object Day1 {
  def main(args: Array[String]): Unit = {
    val app = new Day1()

    // print(app.new_knob_position(99, -100))

    app.run("./src/main/scala/aoc2025/Day1/Example.txt")
    app.run("./src/main/scala/aoc2025/Day1/Input.txt")
  }
}