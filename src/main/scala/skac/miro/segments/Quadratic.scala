package skac.miro.segments

import skac.miro._

/**
 * @author slawek
 */
case class Quadratic(cp: Point, override val end: Point) extends GenericQuadratic(end) {
  // TODO: uzupelnic!
  override def ptAt(arcLen: Double) = ori
}
