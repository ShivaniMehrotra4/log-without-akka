package com.knoldus

object MainOperation extends App {
  val logAnalysis = new LogAnalysisSystem
  val listOfFile = logAnalysis.getListOfFile("/home/knoldus/Documents/SampleFolderLogs")
  println(listOfFile)
  println(logAnalysis.traverseFile(listOfFile, Map("error"->0 , "warn"->0 , "info"->0)))


}
