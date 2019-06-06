package skac.miro.segments

import skac.miro._
import com.github.skac112.vgutils._

/**
 * @author slawek
 */
case class SmoothQuadratic(override val end: Point) extends GenericQuadratic(end) {
  // TODO: uzupelnic!
  override def ptAt(arcLen: Double) = ori
}
