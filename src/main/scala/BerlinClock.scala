package berlin

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


/**
  * some typeclasses for showing the clock in text format
  */
trait BerlinClockShow {
  import cats.Show
  import cats.implicits._

  implicit def lightColourShow[T <: LightColour]: Show[T] = Show.show[T](_.toString.head.toString)

  implicit def optionShow[A <: LightColour](implicit shew: Show[A]): Show[Option[A]] = new Show[Option[A]] {
    def show(a: Option[A]): String = a.fold("_")( shew.show )
  }

  implicit def listShow[A <: LightColour](implicit shew: Show[A]): Show[List[A]] = new Show[List[A]] {
    def show(a: List[A]): String = a.headOption.fold("_")(_ => a.map( shew.show ).mkString )
  }

  implicit val berlinClockShow = new Show[BerlinClock] {
    import shapeless.syntax.std.tuple._

    def show(a: BerlinClock): String = {
      val lights = a.lights.tail.toList
      (a.lights._1.show :: lights.map( _.show )).mkString("\n")
    }
  }
}

object BerlinClockApp extends App with BerlinClockShow {
  import cats.implicits._

  val time = Try{
    LocalTime.parse(args(0), DateTimeFormatter.ISO_TIME)
  }
  val rslt = time.fold(fa => fa.getMessage, BerlinClock(_).show)
  println(rslt)
}