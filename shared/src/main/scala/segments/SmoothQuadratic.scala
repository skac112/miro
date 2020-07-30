package com.github.skac112.miro.segments

import com.github.skac112.gauguin.curves.CubicBezier
import com.github.skac112.miro._
import com.github.skac112.vgutils._

/**
 * @author slawek
 */
case class SmoothQuadratic(override val end: Point) extends GenericQuadratic(end) {
  /**
   * Dummy implementation.
   * @param t
   * @return
   */
  override def apply(t: Double) = ori
}
