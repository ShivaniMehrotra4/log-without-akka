package com.knoldus

import java.io.File

import scala.io.Source

class LogAnalysisSystem extends FileOperations {

  def countErrorsWarningsInfo(file: File): Map[String, Option[Int]] = {
    val fileName = file.toString
    val fSource = Source.fromFile(s"$fileName")
    val countValues = fSource.getLines().flatMap(_.split(" ")).toList.groupBy((word: String) => word).view.mapValues(_.length)
    //println(Map("error" -> countValues.get("[ERROR]")) ++ Map("warn" -> countValues.get("[WARN]")) ++ Map("info" -> countValues.get("[INFO]")))
    Map("error" -> countValues.get("[ERROR]")) ++ Map("warn" -> countValues.get("[WARN]")) ++ Map("info" -> countValues.get("[INFO]"))
  }

  def traverseFile(listOfFiles: List[File], mapCount: Map[String, Int]): Map[String, Int] = {
    listOfFiles match {
      case Nil => mapCount
      case head :: Nil =>
        val x: Map[String, Option[Int]] = countErrorsWarningsInfo(head)
        mapCount ++ Map("error" -> x("error").getOrElse(0)) ++ Map("warn" -> x("warn").getOrElse(0)) ++ Map("Info" -> x("info").getOrElse(0))
      case head :: rest =>
        val x = countErrorsWarningsInfo(head)
        traverseFile(rest, mapCount ++ Map("Error" -> x("error").getOrElse(0))
                           ++ Map("Warning" -> x("warn").getOrElse(0))
                           ++ Map("Info" -> x("info").getOrElse(0)))
    }
  }

}
