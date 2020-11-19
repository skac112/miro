package com.github.skac112.miro.segments

import com.github.skac112.miro._
import scala.math._
import com.github.skac112.vgutils._

object Arc {
  /**
   * Returns circular arc - rx and ry are equal and rotation is meaningless
   */
  def circ(r: Double, laf: Boolean, sf: Boolean, end: Point) = Arc(r, r, .0, laf, sf, end)

  def circ(c: Point, r: Double, startAngle: Angle, angleIncrease: Double) = {
    lazy val large_arc_flag = abs(angleIncrease) > Pi
    lazy val sweep_flag = angleIncrease > 0.0
    circ(r, large_arc_flag, sweep_flag, c + Point.withAngle(startAngle + angleIncrease, r))
  }
}

/**
 * Elliptical arc. Skladnik podsciezek Path.
 * @author slawek
 */
case class Arc(
  rx: Double = 1.0,
  ry: Double = 1.0,
  /* kat osi x */
  rotation: Angle,
  largeArcFlag: Boolean,
  sweepFlag: Boolean,
  override val end: Point)
  extends Segment {

  override def pn(l: Double) = {
    val (init_tau, init_delta_tau, cen) = MathUtils.EllipseCalc.ptToTauParam(ori, end, rx, ry, rotation, largeArcFlag, sweepFlag)
    val init_arclen = MathUtils.EllipseCalc.tauToArcLen(init_tau, rx, ry, sweepFlag)
    val f_arc_len_tau = MathUtils.EllipseCalc.arcLenToTauF(rx, ry, sweepFlag)
    val tau = f_arc_len_tau(init_arclen + l)
    (tauVec(tau) rot rotation) + cen
  }

  /**
   * Calculates vector from arc's ellipse center to point on the ellipse which
   * corresponds to versor (unit vector) with angle tau on a unit circle
   * corresponding to ellipse "before" scaling (rx, ry) and rotation.
   */
  private def tauVec(tau: Double) = Point(rx*cos(tau), ry*sin(tau))

  override def apply(t: Double) = (tauVec(t) rot rotation) + c

  lazy val c = calcCen

  private def calcCen: Point = {
    // transformacja punktow p1 i p2
    val p1p: Point = (end * -0.5) rot -rotation
//    val sign = if (largeArcFlag != sweepFlag) 1 else -1
    val sign = if (largeArcFlag == sweepFlag) 1 else -1
    val a2 = rx*rx
    val b2 = ry*ry
    val p1p2 = Point(p1p.x * p1p.x, p1p.y * p1p.y)
    // srodek elipsy w transformacji ("primowany")
    val cp = Point(rx * p1p.y / ry, -ry*p1p.x / rx) * sign * sqrt((a2*b2 - a2*p1p2.y - b2*p1p2.x) / (a2*p1p2.y + b2*p1p2.x))
    // srodek elipsy po transformacji odwrotnej
    (cp rot rotation) + (end * 0.5)
  }

  lazy val ptToTauParam: (Angle, Double, Point) =
   MathUtils.EllipseCalc.ptToTauParam(ori, end, rx, ry, rotation, largeArcFlag, sweepFlag)

  /**
   * Determines if given angle lies in a given range. Argument rangeLength can
   * be negative - it's sign describes range "direction".
   */
  private def angleInSignedRange(angle: Angle, rangeStart: Angle, rangeLength: Double) = {
    val rangeEnd = Angle(rangeStart.value + rangeLength)
    val (start, end) = if (rangeLength > 0) (rangeStart, Angle(rangeEnd)) else (Angle(rangeEnd), rangeStart)
    angle.between(start, end)
  }

  /**
   * Calculates set of x-extremum points. In general, there can be zero, one or
   * two such points.
   */
  def extrX: Set[Point] = {
    // Ellipse contains 2 x-extremum points laying opposite to each other.
    // Calculating value of parameter tau of one of these points (second one is
    // equal to the first plus Pi modulo 2*Pi)
    def extrT: Double = {
      tan(rotation.value) match {
        case Double.PositiveInfinity => -.5*Pi
        case Double.NegativeInfinity => .5*Pi
        case tan_rot @ _ => -atan(ry/rx * tan_rot)
      }
    }

    val tau1 = Angle(extrT)
    val extrTs = Set(tau1, tau1.opposite)
    val (range_start, range_length, _) = ptToTauParam
    extrTs filter {angleInSignedRange(_, range_start, range_length)} map
     {a => apply(a.value)}
  }

  /**
   * Calculates set of y-extremum points. In general, there can be zero, one or
   * two such points.
   */
  def extrY: Set[Point] = {
    // Ellipse contains 2 y-extremum points laying opposite to each other.
    // Calculating value of parameter tau of one of these points (second one is
    // equal to the first plus Pi modulo 2*Pi)
    def extrT: Double = {
      tan(rotation.value) match {
        case 0 => .5*Pi
        case tan_rot @ _ => -atan(ry/rx / tan_rot)
      }
    }

    val tau1 = Angle(extrT)
    val extrTs = Set(tau1, tau1.opposite)
    val (range_start, range_length, _) = ptToTauParam
    extrTs filter {angleInSignedRange(_, range_start, range_length)} map
     {a => apply(a.value)}
  }

  override lazy val bounds = Bounds.forPts(Set(ori, end) ++ extrX ++ extrY)
}
