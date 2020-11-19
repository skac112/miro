package util

import com.github.skac112.miro.Curve
import com.github.skac112.vgutils._
import com.github.skac112.vgutils.MathUtils._
import scala.math._

object CircArc {
  def apply(
             p1: Point,
             p2: Point,
             radius: Double,
             largeArcFlag: Boolean,
             sweepFlag: Boolean) = {
    val c = calcCenter(p1, p2, radius, largeArcFlag, sweepFlag)
    val start_angle = (p1 - c).angle
    val end_angle = (p2 - c).angle
    val angle_range = end_angle - start_angle
    val flip = !angle_range.isReflex ^ largeArcFlag
    val ang_increase1: Angle = if (flip) angle_range.complement else angle_range
    val ang_increase: Double = if (sweepFlag) ang_increase1.value else -ang_increase1
    new CircArc(c, radius, start_angle, ang_increase)
  }

  private def calcCenter(
                          p1: Point,
                          p2: Point,
                          radius: Double,
                          largeArcFlag: Boolean,
                          sweepFlag: Boolean): Point = {
    val centers = c2pr(p1, p2, radius) toSeq
    val cen1a1 = (p1 - centers(0)).angle
    val cen1a2 = (p2 - centers(0)).angle
    // determines if angle range is small or large counting from p1 to p2 for first candidate center
    val large_arc = (cen1a2 - cen1a1).isReflex
    if (large_arc ^ largeArcFlag ^ sweepFlag) centers(0) else centers(1)
  }
}

/**
 * Circular arc curve.
 * @param center
 * @param radius
 * @param startAngle
 * @param angleIncrease
 */
case class CircArc(center: Point, radius: Double, startAngle: Angle, angleIncrease: Double) extends Curve {
  lazy val startPt = center + Point.withAngle(startAngle, radius)
  lazy val endPt = center + Point.withAngle(startAngle + angleIncrease, radius)
  lazy val largeArcFlag: Boolean = abs(angleIncrease) > Pi
  lazy val sweepFlag: Boolean = angleIncrease > 0.0

  def apply(t: Double) = {
    val pt_angle = Angle(startAngle.value + angleIncrease * t)
    center + Point.withAngle(pt_angle, radius)
  }
}
