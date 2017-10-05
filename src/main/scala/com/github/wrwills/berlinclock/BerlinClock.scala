package com.github.wrwills.berlinclock

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import scala.util.Try

sealed trait LightColour
case object Yellow extends LightColour
case object Red extends LightColour

/**
  * a simple class to represent division and remainder by five
  */
case class FiveOne(i: Int) {
  import scala.math.Integral.Implicits._
  val (fives,ones) = i /% 5
}

case class BerlinClock(pumpOn: Boolean,
                       hours: FiveOne,
                       minutes: FiveOne) {
  import mouse.boolean._

  def lights: (Option[Yellow.type], List[Red.type], List[Red.type], List[LightColour], List[Yellow.type]) =
    (pumpOn.option(Yellow),
      List.fill(hours.fives)(Red),
      List.fill(hours.ones)(Red),
      (1 to minutes.fives).toList.map(x => if (x % 3 == 2) Red else Yellow),
      List.fill(minutes.ones)(Yellow)
      )
}

object BerlinClock {
  def apply(localTime: LocalTime): BerlinClock =
    BerlinClock(
      localTime.getSecond % 2 == 0,
      FiveOne(localTime.getHour),
      FiveOne(localTime.getMinute)
    )

  def apply(hours: Int, minutes: Int, seconds: Int): Try[BerlinClock] =
    Try{ LocalTime.of(hours,minutes,seconds) }.map( BerlinClock(_) )
}
