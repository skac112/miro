package skac.miro.segments

import skac.miro.Point
import skac.miro.transform._
import skac.miro._

/**
 * @author slawek
 */
case class LineSeg(override val end: Point) extends GenericLineSeg(end) {
  override lazy val len = end.modulus
}
