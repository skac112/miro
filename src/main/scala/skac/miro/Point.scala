package skac.miro

import scala.math._
import skac.miro.transform._

object Point {
  var smallDist = 1E-6
  def versor(ang: Angle) = Point(cos(ang), sin(ang))
}

/**
 * @author slawek
 */
case class Point(x: Double, y: Double) {
  import Point._
  def +(Other: Point) = Point(x + Other.x, y + Other.y)
  def -(Other: Point) = Point(x - Other.x, y - Other.y)
  def *(Factor: Double) = Point(x * Factor, y * Factor)
  // iloczyn skalarny
  def *(Other: Point) = x * Other.x + y * Other.y
  // iloczyn wektorowy (skalar bedacy dlugoscia "ze znakiem" wektora iloczynu w osi "z")
  def **(Other: Point) = x * Other.y - y * Other.x
  def /(Factor: Double) = Point(x / Factor, y / Factor)
  lazy val modulus = sqrt(x * x + y * y)
  def dist(Other: Point) = (this - Other).modulus
  lazy val modulus2 = x * x + y * y
  lazy val inv = Point(-x, -y)
  lazy val unary_- = inv
  def closeTo(other: Point) = (this - other).modulus2 < smallDist
  def rot(Deg: Double) = (Rotation(Deg)) transPt this
  def r(deg: Double) = rot(deg)
//  def rot(Deg: Double) = Point(x * cos(Deg) - y * sin(Deg), x * sin(Deg) + y * cos(Deg))
  def rot(Deg: Double, Pivot: Point): Point = Pivot + ((this - Pivot) rot Deg)
  def rot(Rot: Rotation): Point = rot(Rot.angle, Rot.pivot)

  /**
   * Kat skierowany pomiedzy wektorem opisywanym przez ten punkt i wektorem opisywanym przez drugi punkt
   */
  def ang(Other: Point): Angle = Angle(signum(x * Other.y - y * Other.x) * acos(this * Other / modulus / Other.modulus))
  lazy val versor = Point(x / modulus, y / modulus)
}
