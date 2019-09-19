package com.github.skac112.miro.segments

import com.github.skac112.miro._
import com.github.skac112.vgutils._

/**
 * @author slawek
 */
case class SmoothQuadratic(override val end: Point) extends GenericQuadratic(end) {
  // TODO: uzupelnic!
  override def ptAt(arcLen: Double) = ori
}
