package com.github.wrwills.berlinclock

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import scala.util.Try


object BerlinClockApp extends App with BerlinClockShow {
  import cats.implicits._

  val time = Try{
    LocalTime.parse(args(0), DateTimeFormatter.ISO_TIME)
  }
  val rslt = time.fold(fa => fa.getMessage, BerlinClock(_).show)
  println(rslt)
}