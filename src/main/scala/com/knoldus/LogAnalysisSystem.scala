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

  def countLogs: Map[String, Int] = {


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

    traverseFile(getListOfFile("/home/knoldus/Documents/SampleFolderLogs"), Map("error" -> 0, "warn" -> 0, "info" -> 0))
  }

  def calcAverage: Map[String, Int] = {
    val noOfFiles = getListOfFile("/home/knoldus/Documents/SampleFolderLogs").length
    val total = countLogs
    val avgErrors = total.getOrElse("error", 0) / noOfFiles
    val avgWarnings = total.getOrElse("warn", 0) / noOfFiles
    val avgInfo = total.getOrElse("info", 0) / noOfFiles
    Map("avgErrors" -> avgErrors, "avgWarnings" -> avgWarnings, "avgInfo" -> avgInfo)
  }

}
