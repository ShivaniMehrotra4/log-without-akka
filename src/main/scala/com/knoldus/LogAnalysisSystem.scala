package com.knoldus

import java.io.File

import scala.io.Source

class LogAnalysisSystem extends FileOperations {

  def countErrorsWarningsInfo(file: File): Map[String, Option[Int]] = {
    val fileName = file.toString
    val fSource = Source.fromFile(s"$fileName")
    val countValues = fSource.getLines().flatMap(_.split(" ")).toList.groupBy((word: String) => word).view.mapValues(_.length)
    Map("error" -> countValues.get("[ERROR]")) ++ Map("warn" -> countValues.get("[WARN]")) ++ Map("info" -> countValues.get("[INFO]"))
  }

  def traverseFile(listOfFiles: List[File], mapCount: Map[String, Int]): Map[String, Int] = {
    listOfFiles match {
      case Nil => mapCount
      case head :: Nil =>
        val x: Map[String, Option[Int]] = countErrorsWarningsInfo(head)
        val errorCount = mapCount("error")
        val warningCount = mapCount("warn")
        val infoCount = mapCount("info")
        mapCount ++ Map("error" -> (x("error").getOrElse(0) + errorCount)) ++
        Map("warn" -> (x("warn").getOrElse(0) + warningCount)) ++
        Map("info" -> (x("info").getOrElse(0) + infoCount))
      case head :: rest =>
        val x = countErrorsWarningsInfo(head)
        val errorCount = mapCount("error")
        val warningCount = mapCount("warn")
        val infoCount = mapCount("info")
        traverseFile(rest, Map("error" -> (x("error").getOrElse(0) + errorCount)) ++
                           Map("warn" -> (x("warn").getOrElse(0) + warningCount)) ++
                           Map("info" -> (x("info").getOrElse(0) + infoCount)))
    }
  }

}
