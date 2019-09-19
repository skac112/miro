package com.github.skac112.miro.segments

import com.github.skac112.miro._
import com.github.skac112.vgutils._

/**
 * @author slawek
 */
case class SmoothCubic(cp2: Point, override val end: Point) extends GenericCubic(cp2, end) {
  // TODO: uzupelnic!
  override def ptAt(arcLen: Double) = ori
}
