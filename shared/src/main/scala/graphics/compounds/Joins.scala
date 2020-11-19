package graphics.compounds

import com.github.skac112.miro.Graphic.{GenericAttribs, defaultGenericAttribs}
import com.github.skac112.miro.graphics.GenericPath
import com.github.skac112.vgutils.MathUtils.cTanLinePtCirc
import com.github.skac112.vgutils.{Line, LineSec, Point}
import util.CircArc

object Joins {

  /**
   * Creates join graphic for an arc and two points such that generated path:
   * - goes clockwise through p1 and p2 appropriately
   * - ...
   * @param baseArc
   * @param p1
   * @param p2
   * @return
   */
  def baseTop(
               baseArc: CircArc,
               p1: Point,
               p2: Point,
               genericAttribs: GenericAttribs = defaultGenericAttribs): GenericPath = {

    /**
     * Calculates the two and selects one from circles tangent to baseArc and
     * line perpendicular to line passing through tanPt and secondPt with tanPt
     * being the point of tangency with the line
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
        baseArc.center,
        baseArc.radius) toSeq
      // from 2 candidate circles the one for which a vector from p1 to center has negative scalar product with vector
      // from p1 to p2 is chosen
      if ((cand_circles(0)._1 * line_sec) < 0.0) cand_circles(0) else cand_circles(1)
    }

    val circ1 = selectTangentCircle(p1, p2)
    val arc1 = selectTangentArc(circ1, true)
    val circ2 = selectTangentCircle(p2, p1)
    val arc2 = selectTangentArc(circ2, false)



  }
}
