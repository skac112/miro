package skac.miro.segments

import skac.miro._

/**
 * @author slawek
 */
case class SmoothCubic(cp2: Point, override val end: Point) extends GenericCubic(cp2, end) {
  // TODO: uzupelnic!
  override def ptAt(arcLen: Double) = ori
}
