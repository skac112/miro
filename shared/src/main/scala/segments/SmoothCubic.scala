package com.github.skac112.miro.segments

import com.github.skac112.gauguin.curves.CubicBezier
import com.github.skac112.miro._
import com.github.skac112.vgutils._

/**
 * @author slawek
 * Smooth cubic segment. It is not a proper implementation of CubicBezier trait, because its exact shape can't be
 * determined without previous segment in a path and thus apply function can't give appropriate result.
 */
case class SmoothCubic(cnt2: Point, override val end: Point) extends GenericCubic(cnt2, end) with CubicBezier {
  /**
   * Dummy implementation.
   */
  val cnt1 = ori

  /**
   * Dummy implementation.
   * @param t
   * @return
   */
  override def apply(t: Double) = ori
}
