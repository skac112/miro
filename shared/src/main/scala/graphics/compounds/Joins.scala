package com.github.skac112.miro.graphics.compounds

import com.github.skac112.miro.Graphic.{GenericAttribs, defaultGenericAttribs}
import com.github.skac112.miro.graphics.{GenericPath, Path}
import com.github.skac112.miro.segments.{Arc, Cubic, LineSeg}
import com.github.skac112.miro.{GenericSegCurve, PosGraphic, Subpaths}
import com.github.skac112.vgutils.MathUtils.{cTanLineCirc, cTanLinePtCirc}
import com.github.skac112.vgutils.{Angle, LineSec, Point, ori}
import util.CircArc

/**
 * Creates a join - graphic for given circle and two points such that:
 * - it is a path with segments going clockwise through p1 and p2 appropriately,
 * - it contains 4 segments, appropriately (clockwise):
 *   - line section from p1 to p2
 *   - side 1 - 3-rd degree bezier segment tangent in p2 to line perpendicular to line passing through p1 and p2 and
 *     tangent to base arc
 *   - base arc conforming to given base circle
 *   - side 2 - 3-rd degree bezier segment tangent in p1 to line perpendicular to line passing through p1 and p2 and
 *     tangent to base arc
 *  - side segments are 3-d bezier segments created on the basis of previously determined tangent circles
 *    which determine curvature in end points of segment (being points of tangency).
 *    For each side 2 such circles are determined.
 *
 * @param baseCircCen   center of a circle which joins sides (being bezier curves) are tangent to
 * @param baseCircRad   radius of a circle which joins sides (being bezier curves) are tangent to
 * @param p1            Point of tangency of first side of join
 * @param p2            Point of tangency of second side of join
 * @param radiusFactor1 Factor determining curvature of side 1 in point of tangency with base arc
 * @return
 */
object Joins {

  def baseTop[D](
               baseCircCen: Point,
               baseCircRad: Double,
               p1: Point,
               p2: Point,
               radiusFactor1: Double,
               radiusFactor2: Double,
               genericAttribs: GenericAttribs = defaultGenericAttribs): PosGraphic[Path[D], D] = {

    /**
     * Calculates the two and selects one from circles tangent to baseArc and
     * line perpendicular to line passing through tanPt and secondPt with tanPt
     * being the point of tangency with the line
     *
     * @param pTan
     * @return
     */
    def selectTangentCircle(tanPt: Point, secondPt: Point): (Point, Double) = {
      val line_sec = secondPt - tanPt
      // line passing through tanPt perpendicular to line going through tanPt and secondPt
      val tan_line = LineSec(tanPt, secondPt).line.perpendicularBy(tanPt)
      val cand_circles = cTanLinePtCirc(
        tanPt,
        tan_line.a,
        tan_line.b,
        tan_line.c,
        baseCircCen,
        baseCircRad).toSeq
      // from 2 candidate circles the one for which a vector from p1 to center has negative scalar product with vector
      // from p1 to p2 is chosen
      if (((cand_circles(0)._1 - p1) * line_sec) < 0.0) cand_circles(0) else cand_circles(1)
    }

    /**
     * Selects one arc from two being created by cutting given circle by 2 points - one point being given
     * point of tangency with line and second one being the point of tangency of given circle with base circle.
     * Selected arc is always counter-clockwise directed (assuming normal svg coordinate system orientation,
     * where y coordinate grows downward) and it starts on given tanPt or on a point of tangency of
     * given circle with base circle and base arc (according to startOnTanPt argument).
     *
     * @param center
     * @param radius
     * @param tanPt
     * @param sweepFlag
     * @return
     */
    def selectTangentArc(center: Point, radius: Double, tanPt: Point, startOnTanPt: Boolean): CircArc = {
      // angle (relative to center) of point of tanPt
      val angle_1 = (tanPt - center).angle
      // angle (relative to center) of point of tangency with base circle and base arc
      val angle_2 = (baseCircCen - center).angle
      // point of tangency with base circle and base arc
      val tan_pt_2 = center + Point.withAngle(angle_2, radius)
      val (start_angle, angle_increase) = if (startOnTanPt) {
        (angle_1, -(angle_2 - angle_1).value)
      } else {
        (angle_2, -(angle_1 - angle_2).value)
      }
      CircArc(center, radius, start_angle, angle_increase)
    }

    // Calculates bezier curve for one of side of join.
    def side(
              c1cen: Point,
              c1radius: Double,
              tanPt: Point,
              secondPt: Point,
              radiusFactor: Double,
              startOnTanPt: Boolean): Cubic = {

      /**
       * Select one circle (precisely - it's center) from up to 8 circles tangent to given line and
       * base circle. The one with center closest to center of the first tangent circle is chosen.
       *
       * @return
       */
      def selectSecondCircle(centers: Set[Point]): Point =
        centers.minBy { (c: Point) => (c - c1cen).modulus2 }

      val tan_line = LineSec(tanPt, secondPt).line.perpendicularBy(tanPt)
      // radius of second intermediate circle tangent to base circle and arc
      val c2_rad = c1radius * radiusFactor
      // determining center of second intermediate circle tangent to base circle and arc
      // multiple circles possible (up to 8)
      val c2_cand_centers = cTanLineCirc(tan_line.a, tan_line.b, tan_line.c, baseCircCen, baseCircRad, c2_rad)
      // filtering potential intermediate circles lying on the same side of base circle as a first tangent
      val c2_cen = selectSecondCircle(c2_cand_centers)
      // angle of point of tangency tanPt relative to tangent circle 1
      val angle_1 = (tanPt - c1cen).angle
      // +1 if second tangent circle lies outside base circle and -1 otherwise
      val position_sign = math.signum((c2_cen - baseCircCen).modulus2 - baseCircRad * baseCircRad)
      // angle of point of tangency of second circle with base circle and arc (relative to the same second circle)
      // point of tangency of second circle with base circle (and base arc)
      val tan_pt_2 = c2_cen + Point.withAngle(((baseCircCen - c2_cen) * position_sign).angle, c2_rad)
      // angle of point of tangency tan_pt_2
      val angle_2 = (tan_pt_2 - c2_cen).angle
      // angle increase (sort of - because of considering here two different circles),
      // normalizing (done by Angle class constructor) angle to positive value
      val ang_increase_1: Angle = angle_2 - angle_1
      // choosing complement if greater thanPi
      val ang_increase = if (ang_increase_1 isReflex) ang_increase_1.complement else ang_increase_1
      // calculating factor of control point distance / radius for two control point of approximation bezier curve
      // the method is an adaptation of formula given there:
      // https://stackoverflow.com/questions/1734745/how-to-create-circle-with-b%C3%A9zier-curves
      val approx_factor = 4.0 / 3.0 * math.tan(ang_increase / 4.0)
      val (cp1, cp2, end_pt) = if (startOnTanPt) {
        // bezier curve starts on a tanPt and end on a tan_pt_2
        val cp1 = Point.withAngle((p2 - p1).angle + Angle(.5 * math.Pi), approx_factor * c1radius)
        val cp2 = tan_pt_2 + Point.withAngle(angle_2 + Angle(.5 * math.Pi), approx_factor * c2_rad) - tanPt
        val end_pt = tan_pt_2 - tanPt
        (cp1, cp2, end_pt)
      }
      else {
        // bezier curve starts on a tan_pt_2 and ends on a tanPt
        val cp1 = Point.withAngle(angle_2 - Angle(.5 * math.Pi), approx_factor * c2_rad)
        val cp2 = tanPt + Point.withAngle((p2 - p1).angle + Angle(.5 * math.Pi), approx_factor * c1radius) - tan_pt_2
        val end_pt = tanPt - tan_pt_2
        (cp1, cp2, end_pt)
      }
      Cubic(cp1, cp2, end_pt)
    }

    /**
     * Arc segment lying on the base circle between end of side1 bezier curve and start of side2 bezier curve.
     *
     * @param side1
     * @param side2
     * @return
     */
    def tangentArc(side1: Cubic, side2: Cubic): Arc = {
      // 1-st point of tangency with base arc
      val tan_pt_1 = p2 + side1.end
      // 2-nd point of tangency with base arc
      val tan_pt_2 = p1 - side2.end
      // if midpoint between p1 and p2 lies outside base circle, base arc has counter-clockwise orientation within
      // result path and clockwise orientation otherwise
      val sweepFlag = ((p1 + p2) * .5 - baseCircCen).modulus2 < baseCircRad * baseCircRad
      val angle_range = if (sweepFlag) {
        (tan_pt_2 - baseCircCen).angle - (tan_pt_1 - baseCircCen).angle
      } else {
        (tan_pt_1 - baseCircCen).angle - (tan_pt_2 - baseCircCen).angle
      }
      Arc(baseCircRad, baseCircRad, 0.0, angle_range isReflex, sweepFlag, tan_pt_2 - tan_pt_1)
    }

    lazy val lineSeq = LineSeg(p2 - p1)
    lazy val circ1 = selectTangentCircle(p2, p1)
    //    val arc1 = selectTangentArc(circ1._1, circ1._2, p2, true)
    val side1 = side(circ1._1, circ1._2, p2, p1, radiusFactor2, true)
    val circ2 = selectTangentCircle(p1, p2)
    //    val arc2 = selectTangentArc(circ2._1, circ2._2, p1, false)
    val side2 = side(circ2._1, circ2._2, p1, p2, radiusFactor1, false)
    val arc = tangentArc(side1, side2)

    lazy val curve = new GenericSegCurve {
      override lazy val segments = lineSeq :: side1 :: arc :: side2 :: Nil
      override lazy val closed = true
    }

    val subpaths: Subpaths = Seq((curve, ori))
    (new Path(subpaths, genericAttribs), p1)
  }
}
