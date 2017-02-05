package skac.miro

import scala.math._

object Angle {
  def normVal(value: Double): Double = value match {
    case value if value < 0.0 => value + 2 * Pi * Math.ceil(0.5 * value / Pi)
    case value if value > 2 * Pi => value - 2 * Pi * Math.floor(0.5 * value / Pi)
    case _ => value
  }
}

/**
 * Kat w zakresie od 0 do 2*Pi. Nie nadaje sie do reprentacji przyrostow
 * katowych, poniewaz te moga miec dowolnie duza lub mala wartosc (rowniez
 * ujemna).
 * @author slawek
 */
case class Angle(value: Double) {
  import Angle._
  def +(other: Angle) = normalize(value + other.value)
  def -(other: Angle) = normalize(value - other.value)
  def *(factor: Double) = normalize(factor * value)
  def normalize(value: Double): Angle = Angle(Angle.normVal(value))
  lazy val normVal: Double = Angle.normVal(value)
}
