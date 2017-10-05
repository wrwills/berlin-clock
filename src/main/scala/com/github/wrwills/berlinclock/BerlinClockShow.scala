package com.github.wrwills.berlinclock

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
