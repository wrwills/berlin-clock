package com.github.wrwills.berlinclock

import java.time.LocalTime
import org.scalatest.{FlatSpec, Matchers}

class BerlinClockSpec extends FlatSpec with Matchers {

  "A Berlin Clock" should "work" in {
    val clock = BerlinClock(LocalTime.of(14, 28, 1))
    clock.lights shouldBe
      (None, List(Red, Red),
        List(Red, Red, Red, Red),
        List(Yellow, Yellow, Red, Yellow, Yellow),
        List(Yellow, Yellow, Yellow))
  }

  it should "just show the top yellow light at midnight" in {
    val clock = BerlinClock(LocalTime.MIDNIGHT)
    clock.pumpOn shouldBe true
    clock.lights shouldBe(Some(Yellow), List(), List(), List(), List())
  }

  it should "just show the 1 light in each of the bottom rows at 0:06" in {
    val clock = BerlinClock(LocalTime.MIDNIGHT.plusMinutes(6))
    clock.lights shouldBe(Some(Yellow), List(), List(), List(Yellow), List(Yellow))
  }

  it should "show red lights for quarter hour intervals" in {
    val clock = BerlinClock(LocalTime.MIDNIGHT.plusMinutes(55))
    val fiveMinuteLights = clock.lights._4
    fiveMinuteLights.size shouldBe 11
    fiveMinuteLights(2) shouldBe Red
    fiveMinuteLights(5) shouldBe Red
    fiveMinuteLights(8) shouldBe Red
    clock.lights shouldBe
      (Some(Yellow), List(), List(),
        List(Yellow, Yellow, Red, Yellow, Yellow, Red, Yellow, Yellow, Red, Yellow, Yellow), List())
  }
}
