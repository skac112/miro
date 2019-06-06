package skac.miro.segments

import skac.miro._
import com.github.skac112.vgutils._

/**
 * @author slawek
 */
case class LineSeg(override val end: Point) extends GenericLineSeg(end) {
  override lazy val len = end.modulus
}
