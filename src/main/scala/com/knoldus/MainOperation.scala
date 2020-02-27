package com.knoldus

object MainOperation extends App {
  val logAnalysis = new LogAnalysisSystem
  val listOfFile = logAnalysis.getListOfFile("/home/knoldus/Documents/SampleFolderLogs")
  println(listOfFile)
  val resultMap = logAnalysis.countLogs
  println(resultMap)
  val avg = logAnalysis.calcAverage
  println(avg)
}
