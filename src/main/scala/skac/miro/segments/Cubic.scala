package skac.miro.segments

import skac.miro._
import scala.math._
import skac.miro.Bounds._

/**
 * @author slawek
 */
case class Cubic(cp1: Point, cp2: Point, override val end: Point) extends GenericCubic(cp2, end) {
  // TODO: uzupelnic!
  override def ptAt(arcLen: Double) = ori

  /**
   * Point p = (x(t), y(t)) for a given t. t is a parameter of a Bezier curve
   * and this segment is described by (x(t), y(t)) for t in range <0; 1>.
   */
  def ptAtParam(t: Double) = {
    // "reverse" of a parameter t
    lazy val tr = 1 - t
    3*cp1*tr*tr*t + 3*cp2*tr*t*t + end*t*t*t
  }

  /**
   * Some intermediate result used in calculation of x extremum points
   */
  private lazy val imx_1 = -end.x*cp1.x + cp1.x*cp1.x - cp1.x*cp2.x + cp2.x*cp2.x

  private lazy val imx_2 = sqrt(imx_1)

  /**
   * Some intermediate result used in calculation of extremum points
   */
  private lazy val imy_1 = -end.y*cp1.y + cp1.y*cp1.x - cp1.y*cp2.y + cp2.y*cp2.y

  private lazy val imy_2 = sqrt(imy_1)

  private lazy val imx_3 = 2*cp1.x - cp2.x

  private lazy val imy_3 = 2*cp1.y - cp2.y

  private lazy val imx_4 = 1 / (end.x + 3*cp1.x - 3*cp2.x)

  private lazy val imy_4 = 1 / (end.y + 3*cp1.y - 3*cp2.y)

  private def tValid(t: Double) = t >= 0 && t <= 1

  /**
   * Calculates set of x-extremum points. In general, there can be zero, one or
   * two such points.
   */
  def extrX: Set[Point] = {
    val t1 = (imx_3 - imx_2)*imx_4
    val t2 = (imx_3 + imx_2)*imx_4
    for (t <- Set(t1, t2) if tValid(t) && imx_2 >= 0)
    yield ptAtParam(t)
  }

  def extrY: Set[Point] = {
    val t1 = (imy_3 - imy_2)*imy_4
    val t2 = (imy_3 + imy_2)*imy_4
    for (t <- Set(t1, t2) if tValid(t) && imx_2 >= 0)
    yield ptAtParam(t)
  }

  override lazy val bounds = forPts(Set(ori, end) ++ extrX ++ extrY)
}
