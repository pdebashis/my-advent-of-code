package aoc2024.Day6

import scala.io.Source

class Day6 {
  def run(inputFile: String) =  {
    val startTime = System.nanoTime()
    println(s"Executing for ${inputFile.split("/").last}")
    val input = Source.fromFile(inputFile).getLines().toArray

    val start_chars = Map('^' -> (-1,0),'v' -> (1,0),'>' -> (0,1),'<' -> (0,-1))
    val start_loc = (for {
      a <- input.indices
      b <- input(a).indices
      if start_chars.keys.toList.contains(input(a)(b))
    } yield {
      val direction = start_chars.getOrElse(input(a)(b), (0,0))
      ((a,b), direction)
    }).headOption

    def traverse_steps(board: Array[String], start: (Int,Int), dir: (Int,Int)) : Array[String] = {

      val (x,y) = start
      val (all_moves, new_dir, new_dir_letter)  = dir match {
        case (-1,0) => ((x to 0 by -1).toList.map((_,y)),(0,1), '>')
        case (1,0)	=>	((x until board.size by 1).toList.map((_,y)), (0,-1), '<')
        case (0,1) =>  ((y until board.size by 1).toList.map((x,_)), (1,0), 'v')
        case (0,-1) =>	((y to 0 by -1).toList.map((x,_)), (-1,0), '^')
      }


      val out_of_board = all_moves.forall( x => board(x._1)(x._2) != '#')
      val possible_moves = all_moves.takeWhile( x=> board(x._1)(x._2) != '#')
      val replace_coords = possible_moves.drop(1).filter(_ != start_loc.get._1)
      val new_start = possible_moves.last

      if(out_of_board){
        board.zipWithIndex.map{ case (str, i) =>
          (for(j <- str.indices) yield {
            val node = (i, j)
            if(replace_coords.contains(node)) 'X'
            else board(i)(j)
          }).mkString
        }
      } else if(board(new_start._1)(new_start._2) == new_dir_letter) {
        Array[String]()
      } else if(start == new_start){
        traverse_steps(board, new_start, new_dir)
      }
      else{
        val new_board = board.zipWithIndex.map{ case (str, i) =>
          ((for(j <- str.indices) yield {
            val node = (i, j)
            if(replace_coords.contains(node)) 'X'
            else board(i)(j)
          }).mkString, i)
        }.map {  case (str, i) =>
          if(i==new_start._1) {
            str.updated(new_start._2,new_dir_letter)
          }else str
        }

        traverse_steps(new_board, new_start, new_dir)
      }
    }

    def traverse_steps_lite(board: Array[String], start: (Int,Int), dir: (Int,Int)) : Array[String] = {

      val (x,y) = start
      val (all_moves, new_dir, new_dir_letter)  = dir match {
        case (-1,0) => ((x to 0 by -1).toList.map((_,y)),(0,1), '>')
        case (1,0)	=>	((x until board.size by 1).toList.map((_,y)), (0,-1), '<')
        case (0,1) =>  ((y until board.size by 1).toList.map((x,_)), (1,0), 'v')
        case (0,-1) =>	((y to 0 by -1).toList.map((x,_)), (-1,0), '^')
      }

      val out_of_board = all_moves.forall( x => board(x._1)(x._2) != '#')
      val possible_moves = all_moves.takeWhile( x=> board(x._1)(x._2) != '#')
      val replace_coords = possible_moves.drop(1).filter(_ != start_loc.get._1)
      val new_start = possible_moves.last

      if(out_of_board){
        Array("not a Loop")
      } else if(board(new_start._1)(new_start._2) == new_dir_letter) {
        Array[String]()
      } else if(start == new_start){
        traverse_steps(board, new_start, new_dir)
      }
      else{
        val new_board = board.zipWithIndex.map {  case (str, i) =>
          if(i==new_start._1) {
            str.updated(new_start._2,new_dir_letter)
          }else str
        }

        traverse_steps(new_board, new_start, new_dir)
      }
    }

    val traversed_board = traverse_steps(input, start_loc.get._1, start_loc.get._2)

    val output1 = traversed_board.map(_.filter(_ == 'X').size).sum + traversed_board.map(_.filter(start_chars.keys.toList.contains).size).sum

    def add_obstacle(board: Array[String], loc: (Int,Int)) = {
      board.zipWithIndex.map { case (str, i) =>
        (for (j <- str.indices) yield {
          val node = (i, j)
          if (node == start_loc.get._1) board(i)(j)
          else if (node == loc) '#'
          else board(i)(j)
        }).mkString
      }
    }

    val output2 = {
      val possible_obstacles = traversed_board.zipWithIndex.flatMap { case(str,i) =>
        for(j <- str.indices) yield {
          if(traversed_board(i)(j) == 'X') Some((i,j))
          else if(start_chars.keys.toList.contains(traversed_board(i)(j))) Some((i,j))
          else None
        }
      }.filter(_ != None).map(_.get).filter(_ != start_loc.get._1).toSeq

      (for(node <- possible_obstacles) yield {
        val board2_withO = add_obstacle(input,(node._1,node._2))
        val traverse_board2 = traverse_steps_lite(board2_withO, start_loc.get._1, start_loc.get._2)
//        println(s"$node ${traverse_board2.isEmpty}")
        if(traverse_board2.isEmpty) 1
        else 0
      }).sum
    }

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
    app.run("./src/main/scala/aoc2024/Day6/Example.txt")
    app.run("./src/main/scala/aoc2024/Day6/Input.txt")
  }
}