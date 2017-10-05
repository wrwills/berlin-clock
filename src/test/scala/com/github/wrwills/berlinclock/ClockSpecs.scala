package com.github.wrwills.berlinclock

import java.time.LocalTime
import org.scalatest.{FlatSpec,Matchers}

class BerlinClockSpec extends FlatSpec with Matchers {

  "A Berlin Clock" should "work" in {
    val clock = BerlinClock(LocalTime.of(14, 28, 2))
    val lights = clock.lights

    assert(true)
  }

  it should "just show the top yellow light at midnight" in {
    val clock = BerlinClock(LocalTime.MIDNIGHT)
    clock.pumpOn shouldBe true
    clock.lights shouldBe (Some(Yellow),List(),List(),List(),List())
  }

  it should "just show the 1 light in each of the bottom rows at 0:06" in {
    val clock = BerlinClock(LocalTime.MIDNIGHT.plusMinutes(6))
    clock.pumpOn shouldBe true
    clock.lights shouldBe (Some(Yellow),List(),List(),List(Yellow),List(Yellow))
  }

}




