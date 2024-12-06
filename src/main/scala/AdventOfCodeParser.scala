import org.jsoup.Jsoup
import java.io.{File, PrintWriter}
import java.nio.file.{Files, Paths}
import java.nio.charset.StandardCharsets
import scala.io.Source

object AdventOfCodeParser {

    def main(args: Array[String]): Unit = {
        val day = "6"
        val year = "2024"
        val url = s"https://adventofcode.com/$year/day/$day"
        val dest = s"./src/main/scala/aoc$year/Day$day"
        val templateSolution = "./src/main/scala/SolveTemplate.scala"
        val prbStatementPath = s"$dest/Description.md"
        val examplePath = s"$dest/Example.txt"
        val solutionPath = s"$dest/Solution.scala"
        val inputPath = s"$dest/Input.txt"


        val headers = Map(
            "User-Agent" -> "https://github.com/pdebashis"
        )

        try {
            val connection = Jsoup.connect(url)
            headers.foreach { case (key, value) => connection.header(key, value) }
            val document = connection.get()

            val title = document.select(".day-desc h2").text()
            val instructions = document.select("article.day-desc").first()

            val destFile = new File(dest)
            if (!destFile.exists()) destFile.mkdirs()

            val descWriter = new PrintWriter(new File(prbStatementPath))
            val exWriter = new PrintWriter(new File(examplePath))

            descWriter.println(s"# $title\n")

            if (instructions != null) {
                instructions.children().forEach { element =>
                    element.tagName() match {
                        case "p" => descWriter.println(element.text() + "\n")
                        case "pre" =>
                            descWriter.println("```shell")
                            descWriter.println(element.text())
                            descWriter.println("```\n")
                            exWriter.println(element.text())
                        case _ =>
                    }
                }
            }
            descWriter.close()
            exWriter.close()
            println(s"Content saved at: $prbStatementPath")

            if (Files.exists(Paths.get(solutionPath))) {
                println(s"File already exists at: $solutionPath")
            } else {
                Files.copy(Paths.get(templateSolution),Paths.get(solutionPath))
                val content = Source.fromFile(solutionPath)(StandardCharsets.UTF_8).mkString
                val packageName = s"package aoc${year}.Day${day}\n\n"
                val updatedContent = packageName.appendedAll(content).replace("???", s"Day${day}")
                  .replace("%YEAR%", year)
                  .replace("%DAY%",day)
                Files.write(Paths.get(solutionPath), updatedContent.getBytes(StandardCharsets.UTF_8))
                println(s"Solution template cloned to: $solutionPath")
            }

            val inputWriter = new PrintWriter(inputPath)
            inputWriter.close()
            println(s"Created Empty Input File: $inputPath")

        } catch {
            case e: Exception =>
                println(s"Error occurred: ${e.getMessage}")
        }
    }
}