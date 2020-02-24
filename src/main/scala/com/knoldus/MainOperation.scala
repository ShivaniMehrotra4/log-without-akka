package com.knoldus

object MainOperation extends App {
  val logAnalysis = new LogAnalysisSystem
  val listOfFile = logAnalysis.getListOfFile("/home/knoldus/Documents/SampleFolderLogs")
  println(listOfFile)
  val resultMap = logAnalysis.traverseFile(listOfFile, Map("error" -> 0, "warn" -> 0, "info" -> 0))
  println(resultMap)
  val avg = listOfFile.length
  val avgErrors = resultMap.getOrElse("error", 0) / avg
  val avgWarnings = resultMap.getOrElse("warn", 0) / avg
  val avgInformation = resultMap.getOrElse("info", 0) / avg

  println(avgErrors)
  println(avgWarnings)
  println(avgInformation)
}
