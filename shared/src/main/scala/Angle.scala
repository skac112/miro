package com.github.skac112.miro

import scala.math._

object Angle_old {
  def normVal(value: Double): Double = value match {
    case value if value < 0.0 => value + 2 * Pi * Math.ceil(0.5 * abs(value) / Pi)
    case value if value > 2 * Pi => value - 2 * Pi * Math.floor(0.5 * value / Pi)
    case _ => value
  }

  lazy val dtrF = Pi / 180.0

  lazy val rtdF = 180.0 / Pi

  def fromDeg(deg: Double) = Angle_old(dtrF * deg)
}

/**
 * Kat w zakresie od 0 do 2*Pi. Nie nadaje sie do reprentacji przyrostow
 * katowych, poniewaz te moga miec dowolnie duza lub mala wartosc (rowniez
 * ujemna).
 * @author slawek
 */
case class Angle_old(value: Double) {
  import Angle_old._
  lazy val unary_- = Angle_old(2*Pi - normVal)
  def +(other: Angle_old) = normalize(value + other.value)
  def -(other: Angle_old) = normalize(value - other.value)
  def *(factor: Double) = normalize(factor * value)
  def normalize(value: Double): Angle_old = Angle_old(Angle_old.normVal(value))
  lazy val normVal: Double = Angle_old.normVal(value)
  def toDeg = rtdF * value
  def opposite = normalize(value + Pi)

  /**
   * Determines if this angle lies in a given range. Range spans from start to
   * end in positive direction.
   */
  def between(start: Angle_old, end: Angle_old): Boolean = {
    val sn = start.normVal
    val en = start.normVal
    val n = normVal
    (sn <= en && n >= sn && n <= en) || (sn >= en && (n >= sn || n <= en))
  }
}
