package com.knoldus

import org.scalatest._

class LogAnalysisSystemSpec extends FunSuite with BeforeAndAfterAll {

  var logAnalysis: LogAnalysisSystem = _

  override def beforeAll(): Unit = {
    logAnalysis = new LogAnalysisSystem
  }

  test("testing total countLogs") {
    val actual = logAnalysis.countLogs
    val expected = Map("error" -> 0, "warn" -> 1535, "info" -> 265)
    assert(actual == expected)
  }

  test("testing average") {
    val actual = logAnalysis.calcAverage
    val expected = Map("avgErrors" -> 0, "avgWarnings" -> 191, "avgInfo" -> 33)
    assert(actual == expected)
  }

}