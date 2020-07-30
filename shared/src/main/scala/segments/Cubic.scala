package com.github.skac112.miro.segments

import com.github.skac112.gauguin.curves.CubicBezier
import com.github.skac112.miro._

import scala.math._
import com.github.skac112.vgutils._

/**
 * @author slawek
 */
case class Cubic(override val cnt1: Point, override val cnt2: Point, override val end: Point) extends GenericCubic(cnt2, end) with CubicBezier {
  override val start = ori
  
  /**
   * Some intermediate result used in calculation of x extremum points
   */
  private lazy val imx_1 = -end.x*cnt1.x + cnt1.x*cnt1.x - cnt1.x*cnt2.x + cnt2.x*cnt2.x

  private lazy val imx_2 = sqrt(imx_1)

  /**
   * Some intermediate result used in calculation of extremum points
   */
  private lazy val imy_1 = -end.y*cnt1.y + cnt1.y*cnt1.x - cnt1.y*cnt2.y + cnt2.y*cnt2.y

  private lazy val imy_2 = sqrt(imy_1)

  private lazy val imx_3 = 2*cnt1.x - cnt2.x

  private lazy val imy_3 = 2*cnt1.y - cnt2.y

  private lazy val imx_4 = 1 / (end.x + 3*cnt1.x - 3*cnt2.x)

  private lazy val imy_4 = 1 / (end.y + 3*cnt1.y - 3*cnt2.y)

  private def tValid(t: Double) = t >= 0 && t <= 1

  /**
   * Calculates set of x-extremum points. In general, there can be zero, one or
   * two such points.
   */
  def extrX: Set[Point] = {
    val t1 = (imx_3 - imx_2)*imx_4
    val t2 = (imx_3 + imx_2)*imx_4
    for (t <- Set(t1, t2) if tValid(t) && imx_2 >= 0)
    yield apply(t)
  }

  /**
    * Calculates set of y-extremum points. In general, there can be zero, one or
    * two such points.
    */
  def extrY: Set[Point] = {
    val t1 = (imy_3 - imy_2)*imy_4
    val t2 = (imy_3 + imy_2)*imy_4
    for (t <- Set(t1, t2) if tValid(t) && imx_2 >= 0)
    yield apply(t)
  }

  override lazy val bounds = Bounds.forPts(Set(ori, end) ++ extrX ++ extrY)
}
