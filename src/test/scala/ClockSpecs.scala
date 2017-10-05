package berlin

import java.time.LocalTime

import org.scalatest.junit.{JUnitRunner, JUnitSuite}
import org.scalatest.prop.{Checkers, PropertyChecks}
import org.scalatest.{FlatSpec, FunSuite, Matchers}

class BerlinClockSpec extends FlatSpec with Matchers {

  "A Berlin Clock" should "work" in {
    val clock = BerlinClock(LocalTime.of(14, 28, 2))
    val lights = clock.lights

    //lights.
    //println(clock.lights)
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


  /*
  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new Stack[Int]
    stack.push(1)
    stack.push(2)
    assert(stack.pop() === 2)
    assert(stack.pop() === 1)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new Stack[String]
    assertThrows[NoSuchElementException] {
      emptyStack.pop()
    }
  }*/
}

import org.scalatest.junit.JUnitSuite
import org.scalatest.prop.Checkers
import org.scalacheck
//import org.scalacheck.Arbitrary._
import org.scalacheck.Arbitrary
import org.scalacheck.Gen
//import org.scalacheck.Arbitrary._
import org.scalacheck.Prop._
import org.scalacheck.Prop
import org.scalacheck.Gen
//import org.scalacheck.Test.Params
import org.scalatest.prop.Checkers._

  class BerlinClockProperties extends FunSuite with Checkers with Matchers with BerlinClockShow {


  import cats.implicits._

  implicit override val generatorDrivenConfig = PropertyCheckConfig(minSuccessful = 500, minSize = 0, maxSize = 1000)

  implicit def arbInterval = Arbitrary(Gen.choose(0, LocalTime.MAX.toSecondOfDay / 60))

  test("works for all seconds") {
    check { (n: Int) => {
      val output = BerlinClock(LocalTime.ofSecondOfDay(n * 60)).show
      //println(output)
      // ignore the top seconds lamp
      val counts = output.lines.map(_.replaceAll("_", "").size).toSeq.tail
      val totalCount = counts(0) * 60 * 5 + counts(1) * 60 + counts(2) * 5 + counts(3)
      totalCount ==  n // should equal(n)
    }
    }
  }

  }



